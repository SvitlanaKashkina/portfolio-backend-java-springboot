package com.kashkina.portfolio.service;

import com.kashkina.portfolio.entity.contact.ContactMessage;
import com.kashkina.portfolio.kafka.event.VisitEvent;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @SneakyThrows
    public void sendContactMessageEmail(ContactMessage contact) {
        log.info("Call sendContactMessageEmail");

        StringBuilder text = new StringBuilder();
        text.append("Name: ").append(contact.getName()).append("\n")
                .append("Email: ").append(contact.getEmail()).append("\n")
                .append("Phone: ").append(contact.getPhone() != null ? contact.getPhone() : "not specified").append("\n")
                .append("Subject: ").append(contact.getSubject() != null ? contact.getSubject() : "not specified").append("\n")
                .append("Consent to data processing: ").append(contact.getConsent() ? "Yes" : "No").append("\n")
                .append("Message: ").append(contact.getMessage()).append("\n")
                .append("Sent: ").append(contact.getCreatedAt());

        SimpleMailMessage message = new SimpleMailMessage();
        String toEmail = System.getenv("SPRING_MAIL_USERNAME");
        message.setTo(toEmail);
        message.setSubject("New post from the Portfolio website: " + (contact.getSubject() != null ? contact.getSubject() : "No theme"));
        message.setText(text.toString());

        // send and rethrow the exception further
        try {
            mailSender.send(message);
            log.info("Email sent successfully");
        } catch (RuntimeException e) { // ловим все unchecked ошибки, включая MailException
            log.error("Failed to send email", e);
        }
    }

    // Kafka E-mail
    @SneakyThrows
    public void sendVisitNotification(VisitEvent event, int totalSessions, Map<String, Integer> pageVisits)  {
        StringBuilder text = new StringBuilder();
        text.append("A new user visited your portfolio site!\n\n")
                .append("Session ID: ").append(event.getSessionId()).append("\n")
                .append("Timestamp: ").append(event.getTimestamp()).append("\n\n")
                .append("Total unique site visits so far: ").append(totalSessions).append("\n\n")
                .append("Page visit counts:\n");

        pageVisits.forEach((page, count) -> text.append(page).append(": ").append(count).append("\n"));

        SimpleMailMessage message = new SimpleMailMessage();
        String toEmail = System.getenv("SPRING_MAIL_USERNAME");
        message.setTo(toEmail);
        message.setSubject("Portfolio site visit notification: new user");
        message.setText(text.toString());

        try {
            mailSender.send(message);
            log.info("Email sent successfully");
        } catch (RuntimeException e) {
            log.error("Failed to send email", e);
        }
    }
}