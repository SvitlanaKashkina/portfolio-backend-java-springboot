package com.kashkina.portfolio.controller;

import com.kashkina.portfolio.dto.contact.ContactMessageDTO;
import com.kashkina.portfolio.entity.contact.ContactMessage;
import com.kashkina.portfolio.service.ContactMessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/contact")
@RequiredArgsConstructor
public class ContactMessageController {

    private static final Logger log = LoggerFactory.getLogger(ContactMessageController.class);

    private final ContactMessageService service;

    @PostMapping
    public ResponseEntity<?> createContactMessage(@Valid @RequestBody ContactMessageDTO dto) {
        log.info("POST /api/contact called with payload: {}", dto);
        try {
            ContactMessage savedMessage = service.saveContactMessage(dto);

            log.info("Contact message saved successfully with id: {}", savedMessage.getId());
            log.debug("Saved contact message details: {}", savedMessage);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedMessage);
        } catch (IllegalArgumentException e) {
            log.warn("Validation failed while saving contact message: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error while saving contact message", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred. Please try again later.");
        }
    }
}
