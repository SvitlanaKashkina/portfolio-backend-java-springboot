package com.kashkina.portfolio.controller;

import com.kashkina.portfolio.dto.projects.ProjectDto;
import com.kashkina.portfolio.kafka.event.VisitEvent;
import com.kashkina.portfolio.kafka.producer.VisitEventProducer;
import com.kashkina.portfolio.service.ProjectService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private static final Logger log = LoggerFactory.getLogger(ProjectController.class);

    private final ProjectService projectService;
    private final VisitEventProducer visitEventProducer; // Kafka Producer

    @GetMapping
    public List<ProjectDto> getProjects(HttpSession session) {
        log.info("GET /API/PROJECTS CALLED");

        List<ProjectDto> projects = projectService.getAllProjects();

        if (projects.isEmpty()) {
            log.warn("No projects found in database");
        } else {
            log.info("Returning {} projects successfully", projects.size());
            log.debug("Projects details: {}", projects);
        }

        // Send an event to Kafka on every page visit
        VisitEvent event = new VisitEvent(
                session.getId(),
                "/api/projects",
                LocalDateTime.now()
        );

        visitEventProducer.sendVisitEventAsync(event);

        return projects;
    }
}

