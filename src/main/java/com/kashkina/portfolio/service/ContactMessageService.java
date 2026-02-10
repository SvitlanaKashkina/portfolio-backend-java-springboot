package com.kashkina.portfolio.service;

import com.kashkina.portfolio.dto.contact.ContactMessageDTO;
import com.kashkina.portfolio.entity.contact.ContactMessage;
import com.kashkina.portfolio.exception.EmailSendException;
import com.kashkina.portfolio.kafka.event.VisitEvent;
import com.kashkina.portfolio.kafka.producer.VisitEventProducer;
import com.kashkina.portfolio.repository.contact.ContactMessageRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ContactMessageService {

    private static final Logger log = LoggerFactory.getLogger(ContactMessageService.class);

    private final ContactMessageRepository repository;
    private final EmailService emailService;
    private final VisitEventProducer visitEventProducer; // Kafka producer

    public ContactMessage saveContactMessage(ContactMessageDTO dto) {
        log.info("Starting to save contact message from: {} <{}>", dto.getName(), dto.getEmail());

        if (dto.getConsent() == null || !dto.getConsent()) {
            log.warn("Consent not given by user: {} <{}>", dto.getName(), dto.getEmail());
            throw new IllegalArgumentException("DSGVO consent is required");
        }

        ContactMessage message = ContactMessage.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .subject(dto.getSubject())
                .message(dto.getMessage())
                .phone(dto.getPhone())
                .consent(dto.getConsent())
                .build();

        log.debug("ContactMessage entity created: {}", message);

        ContactMessage savedMessage = repository.save(message);
        log.info("Contact message saved successfully with id: {}", savedMessage.getId());

        return savedMessage;
    }

    //Asynchronous Post-Save Processing: Email and Kafka
    @Async("taskExecutor")
    public void processAfterSaveAsync(ContactMessage message, HttpSession session) {
        try {
            emailService.sendContactMessageEmail(message);
            log.info("Email sent successfully for {}", message.getEmail());
        } catch (Exception e) {
            log.error("Failed to send email for {}", message.getEmail(), e);
        }

        try {
            VisitEvent event = new VisitEvent(
                    session.getId(),
                    "/api/contact",
                    LocalDateTime.now()
            );
            visitEventProducer.sendVisitEventAsync(event);
            log.info("VisitEvent sent to Kafka for session {}", session.getId());
        } catch (Exception e) {
            log.error("Failed to send VisitEvent to Kafka for session {}", session.getId(), e);
        }
    }
}
