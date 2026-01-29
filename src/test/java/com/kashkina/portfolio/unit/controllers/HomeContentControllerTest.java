package com.kashkina.portfolio.unit.controllers;

import com.kashkina.portfolio.controller.HomeContentController;
import com.kashkina.portfolio.entity.home.HomeContent;
import com.kashkina.portfolio.kafka.producer.VisitEventProducer;
import com.kashkina.portfolio.repository.home.HomeContentRepository;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HomeContentControllerTest {

    private HomeContentController controller;
    private HomeContentRepository repository;
    private VisitEventProducer visitEventProducer;
    private HttpSession session;

    @BeforeEach
    void setUp() {
        repository = mock(HomeContentRepository.class); // mock repository
        controller = new HomeContentController(repository, visitEventProducer); // controller with mock repository
    }

    @Test
    void testGetHomeContent_Success() {
        // Create an object with real HomeContentDTO fields
        HomeContent content = new HomeContent();
        content.setFullName("Alice Example");
        content.setRoleTitle("Developer");
        content.setRoleType("Frontend");
        content.setShortBio("I build cool apps");
        content.setGithubUrl("https://github.com/alice");
        content.setLinkedinUrl("https://linkedin.com/in/alice");

        // Setting up a mock: findById(1L) returns Optional.of(content)
        when(repository.findById(1)).thenReturn(Optional.of(content));

        // Calling the controller method
        HomeContent result = controller.getHomeContent(session);

        // We check that the expected object was returned
        assertNotNull(result);
        assertEquals("Alice Example", result.getFullName());
        assertEquals("Developer", result.getRoleTitle());
        assertEquals("Frontend", result.getRoleType());
        assertEquals("I build cool apps", result.getShortBio());
        assertEquals("https://github.com/alice", result.getGithubUrl());
        assertEquals("https://linkedin.com/in/alice", result.getLinkedinUrl());

        // Checking the repository call
        verify(repository, times(1)).findById(1);
    }

    @Test
    void testGetHomeContent_NotFound() {
        // Setting up a mock: the repository returns an empty Optional
        when(repository.findById(1)).thenReturn(Optional.empty());

        // Check that a RuntimeException is thrown
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> controller.getHomeContent(session));

        assertEquals("Home content not found", exception.getMessage());

        verify(repository, times(1)).findById(1);
    }
}