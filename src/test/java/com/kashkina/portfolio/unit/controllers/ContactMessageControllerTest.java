package com.kashkina.portfolio.unit.controllers;

import com.kashkina.portfolio.controller.ContactMessageController;
import com.kashkina.portfolio.dto.contact.ContactMessageDTO;
import com.kashkina.portfolio.entity.contact.ContactMessage;
import com.kashkina.portfolio.kafka.producer.VisitEventProducer;
import com.kashkina.portfolio.repository.contact.ContactMessageRepository;
import com.kashkina.portfolio.service.ContactMessageService;
import com.kashkina.portfolio.service.EmailService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ContactMessageControllerTest {

    private ContactMessageController controller;
    private ContactMessageService service;
    private ContactMessageRepository repository;
    private EmailService emailService;
    private VisitEventProducer visitEventProducer;
    private HttpSession session;

    @BeforeEach
    void setUp() {
        service = mock(ContactMessageService.class); // creating a mock service
        controller = new ContactMessageController(service, repository, emailService, visitEventProducer); // Create a controller with a mock service
    }

    @Test
    void testCreateContactMessage_Success() {
        // Preparing a DTO for the request
        ContactMessageDTO dto = new ContactMessageDTO();
        dto.setName("Alice");
        dto.setEmail("alice@example.com");
        dto.setMessage("Hello!");

        // We prepare the object that the service should return
        ContactMessage savedMessage = new ContactMessage();
        savedMessage.setId(1);
        savedMessage.setName(dto.getName());
        savedMessage.setEmail(dto.getEmail());
        savedMessage.setMessage(dto.getMessage());

        // Setting up a mock: when the service is called, return savedMessage
        when(service.saveContactMessage(dto)).thenReturn(savedMessage);

        // Calling the controller method
        ResponseEntity<?> response = controller.createContactMessage(dto, session);

        // Checking the status and response body
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(savedMessage, response.getBody());

        // We check that the service was called exactly once
        verify(service, times(1)).saveContactMessage(dto);
    }

    @Test
    void testCreateContactMessage_ValidationError() {
        ContactMessageDTO dto = new ContactMessageDTO();
        dto.setName("");

        // Setting up a mock: the service throws an IllegalArgumentException
        when(service.saveContactMessage(dto))
                .thenThrow(new IllegalArgumentException("Name cannot be empty"));

        // Calling the controller method
        ResponseEntity<?> response = controller.createContactMessage(dto, session);

        // Checking the status and error message
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Name cannot be empty", response.getBody());

        verify(service, times(1)).saveContactMessage(dto);
    }

    @Test
    void testCreateContactMessage_UnexpectedError() {
        ContactMessageDTO dto = new ContactMessageDTO();
        dto.setName("Alice");
        dto.setEmail("alice@example.com");
        dto.setMessage("Hello!");

        // Setting up a mock: the service throws an unexpected error
        when(service.saveContactMessage(dto)).thenThrow(new RuntimeException("Database down"));

        ResponseEntity<?> response = controller.createContactMessage(dto, session);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An unexpected error occurred. Please try again later.", response.getBody());

        verify(service, times(1)).saveContactMessage(dto);
    }
}