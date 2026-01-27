package com.kashkina.portfolio.service;

import com.kashkina.portfolio.dto.contact.ContactMessageDTO;
import com.kashkina.portfolio.entity.contact.ContactMessage;
import com.kashkina.portfolio.repository.contact.ContactMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class ContactMessageService {

    private static final Logger log = LoggerFactory.getLogger(ContactMessageService.class);

    private final ContactMessageRepository repository;

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
}
