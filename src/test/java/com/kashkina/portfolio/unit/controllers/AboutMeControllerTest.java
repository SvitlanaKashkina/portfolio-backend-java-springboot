package com.kashkina.portfolio.unit.controllers;

import com.kashkina.portfolio.controller.AboutMeController;
import com.kashkina.portfolio.dto.about.AboutMeDTO;
import com.kashkina.portfolio.dto.about.AboutMeResponseDTO;
import com.kashkina.portfolio.kafka.producer.VisitEventProducer;
import com.kashkina.portfolio.service.AboutMeService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AboutMeControllerTest {

    private AboutMeController controller;
    private AboutMeService service; // controller dependency
    private VisitEventProducer visitEventProducer;
    private HttpSession session;


    @BeforeEach   // Runs before each test, prepares objects
    void setUp() {
        service = mock(AboutMeService.class); // creating a mock service
        controller = new AboutMeController(service, visitEventProducer); // Create a controller with a mock service
    }

    @Test
    void testGetAboutMe() {
        // Create a fiction response object
        AboutMeDTO section = new AboutMeDTO();
        section.setTitle("Alice");
        section.setContent("Developer");

        AboutMeResponseDTO responseDTO = new AboutMeResponseDTO();
        responseDTO.setSections(List.of(section));
        responseDTO.setCertificates(List.of());

        // Setting up a mock: When the service is called, return a fiction object
        when(service.getAboutMeContent()).thenReturn(responseDTO);

        // Calling the controller method
        AboutMeResponseDTO result = controller.getAboutMe(session);

        // check the result
        assertNotNull(result);
        assertNotNull(result.getSections());
        assertEquals(1, result.getSections().size());

        assertEquals("Alice", result.getSections().get(0).getTitle());
        assertEquals("Developer", result.getSections().get(0).getContent());

        // check that the service method was called exactly once
        verify(service, times(1)).getAboutMeContent();
    }
}