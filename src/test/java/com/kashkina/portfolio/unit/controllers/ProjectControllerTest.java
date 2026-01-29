package com.kashkina.portfolio.unit.controllers;

import com.kashkina.portfolio.controller.ProjectController;
import com.kashkina.portfolio.dto.projects.ProjectDto;
import com.kashkina.portfolio.dto.projects.ProjectFeatureDto;
import com.kashkina.portfolio.dto.projects.ProjectScreenshotDto;
import com.kashkina.portfolio.dto.projects.TechnologyDto;
import com.kashkina.portfolio.kafka.producer.VisitEventProducer;
import com.kashkina.portfolio.service.ProjectService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjectControllerTest {

    private ProjectController controller;
    private ProjectService projectService;
    private VisitEventProducer visitEventProducer;
    private HttpSession session;

    @BeforeEach
    void setUp() {
        projectService = mock(ProjectService.class);
        controller = new ProjectController(projectService, visitEventProducer, session);
    }

    @Test
    void testGetProjects_Success() {
        // prepare Technologies, Features and Screenshots
        TechnologyDto tech1 = new TechnologyDto(1, "Java", "Backend");
        TechnologyDto tech2 = new TechnologyDto(2, "React", "Frontend");

        ProjectFeatureDto feature1 = new ProjectFeatureDto(1, "Login system", 1);
        ProjectFeatureDto feature2 = new ProjectFeatureDto(2, "Chat functionality", 2);

        ProjectScreenshotDto screenshot1 = new ProjectScreenshotDto(1, "url1.png", "Home page", 1);

        // Preparing ProjectDto
        ProjectDto project = ProjectDto.builder()
                .id(1)
                .title("Portfolio Website")
                .shortDescription("My portfolio")
                .fullDescription("Full description of the project")
                .architectureDescription("MVC architecture")
                .roleDescription("Frontend developer")
                .lessonsLearned("Testing, React, Java")
                .status("Completed")
                .endDate(LocalDate.of(2025, 1, 28))
                .githubUrl("https://github.com/alice/portfolio")
                .technologies(List.of(tech1, tech2))
                .features(List.of(feature1, feature2))
                .screenshots(List.of(screenshot1))
                .build();

        List<ProjectDto> projectList = List.of(project);

        // Setting up a mock: getAllProjects() returns a list
        when(projectService.getAllProjects()).thenReturn(projectList);

        // Calling the controller method
        List<ProjectDto> result = controller.getProjects(session);

        // check the result
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Portfolio Website", result.get(0).getTitle());
        assertEquals("My portfolio", result.get(0).getShortDescription());
        assertEquals("Full description of the project", result.get(0).getFullDescription());
        assertEquals("MVC architecture", result.get(0).getArchitectureDescription());
        assertEquals("Frontend developer", result.get(0).getRoleDescription());
        assertEquals("Testing, React, Java", result.get(0).getLessonsLearned());
        assertEquals("Completed", result.get(0).getStatus());
        assertEquals(LocalDate.of(2025, 1, 28), result.get(0).getEndDate());
        assertEquals("https://github.com/alice/portfolio", result.get(0).getGithubUrl());

        assertEquals(2, result.get(0).getTechnologies().size());
        assertEquals(2, result.get(0).getFeatures().size());
        assertEquals(1, result.get(0).getScreenshots().size());

        // check that the service is called exactly once
        verify(projectService, times(1)).getAllProjects();
    }

    @Test
    void testGetProjects_EmptyList() {
        when(projectService.getAllProjects()).thenReturn(List.of());

        List<ProjectDto> result = controller.getProjects(session);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(projectService, times(1)).getAllProjects();
    }
}