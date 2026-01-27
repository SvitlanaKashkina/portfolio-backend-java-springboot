package com.kashkina.portfolio.controller;

import com.kashkina.portfolio.dto.projects.ProjectDto;
import com.kashkina.portfolio.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private static final Logger log = LoggerFactory.getLogger(ProjectController.class);

    private final ProjectService projectService;

    @GetMapping
    public List<ProjectDto> getProjects() {
        log.info("GET /api/projects called");

        List<ProjectDto> projects = projectService.getAllProjects();

        if (projects.isEmpty()) {
            log.warn("No projects found in database");
        } else {
            log.info("Returning {} projects successfully", projects.size());
            log.debug("Projects details: {}", projects);
        }

        return projects;
    }
}

