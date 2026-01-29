package com.kashkina.portfolio.service;

import com.kashkina.portfolio.entity.contact.ContactMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendContactMessageEmail(ContactMessage contact) throws MailException {

        System.out.println("MAIL USER = " + System.getenv("SPRING_MAIL_USERNAME"));
        System.out.println("MAIL PASS = " + System.getenv("SPRING_MAIL_PASSWORD"));

        StringBuilder text = new StringBuilder();
            text.append("Name: ").append(contact.getName()).append("\n")
                .append("Email: ").append(contact.getEmail()).append("\n")
                .append("Phone: ").append(contact.getPhone() != null ? contact.getPhone() : "not specified").append("\n")
                .append("Subject: ").append(contact.getSubject() != null ? contact.getSubject() : "not specified").append("\n")
                .append("Consent to data processing: ").append(contact.getConsent() ? "Yes" : "No").append("\n")
                .append("Message: ").append(contact.getMessage()).append("\n")
                .append("Sent: ").append(contact.getCreatedAt());

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("svitlana.kashkina@gmail.com");
        message.setSubject("New post from the Portfolio website: " + (contact.getSubject() != null ? contact.getSubject() : "No theme"));
        message.setText(text.toString());

        // send and rethrow the exception further
        mailSender.send(message);
    }
}