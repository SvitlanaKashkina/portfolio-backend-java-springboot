package com.kashkina.portfolio.service;

import com.kashkina.portfolio.dto.projects.ProjectDto;
import com.kashkina.portfolio.dto.projects.ProjectFeatureDto;
import com.kashkina.portfolio.dto.projects.ProjectScreenshotDto;
import com.kashkina.portfolio.dto.projects.TechnologyDto;
import com.kashkina.portfolio.entity.projects.Project;
import com.kashkina.portfolio.repository.projects.ProjectRepository;
import com.kashkina.portfolio.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private static final Logger log = LoggerFactory.getLogger(ProjectService.class);

    private final ProjectRepository projectRepository;

    public List<ProjectDto> getAllProjects() {
        log.info("Fetching all projects from the database");

        List<Project> projects = projectRepository.findAll();

        if (projects.isEmpty()) {
            log.error("No projects found in the database");
            throw new DataNotFoundException("No projects found in the database");
        }

        log.info("Found {} projects", projects.size());
        log.debug("Projects fetched: {}", projects);

        List<ProjectDto> dtos = projects.stream()
                .map(this::mapToDto)
                .toList();

        log.info("Returning {} ProjectDTOs successfully", dtos.size());
        log.debug("ProjectDTO details: {}", dtos);

        return dtos;
    }

    private ProjectDto mapToDto(Project project) {
        log.debug("Mapping project with id {} to DTO", project.getId());

        if (project.getTitle() == null ||
                project.getShortDescription() == null ||
                project.getFullDescription() == null) {
            log.error("Project with id {} has missing required fields", project.getId());
            throw new DataNotFoundException(
                    "Project with id " + project.getId() + " has missing required fields"
            );
        }

        ProjectDto dto = new ProjectDto();
        dto.setId(project.getId());
        dto.setTitle(project.getTitle());
        dto.setShortDescription(project.getShortDescription());
        dto.setFullDescription(project.getFullDescription());
        dto.setArchitectureDescription(project.getArchitectureDescription());
        dto.setRoleDescription(project.getRoleDescription());
        dto.setLessonsLearned(project.getLessonsLearned());
        dto.setStatus(project.getStatus());
        dto.setEndDate(project.getEndDate());
        dto.setGithubUrl(project.getGithubUrl());

        // Technologies (Set -> Set)
        dto.setTechnologies(
                project.getTechnologies() == null
                        ? List.of() // если null
                        : project.getTechnologies().stream()
                        .map(t -> new TechnologyDto(t.getId(), t.getName(), t.getCategory()))
                        .toList()
        );

        // Features (Set -> Set)
        dto.setFeatures(
                Optional.ofNullable(project.getFeatures())
                        .orElse(Set.of())
                        .stream()
                        .map(f -> new ProjectFeatureDto(f.getId(), f.getDescription(), f.getDisplayOrder()))
                        .sorted(Comparator.comparing(ProjectFeatureDto::displayOrder))
                        .toList()
        );

        // Screenshots (Set -> Set)
        dto.setScreenshots(
                Optional.ofNullable(project.getScreenshots())
                        .orElse(Set.of())    // Set из сущности
                        .stream()
                        .map(s -> new ProjectScreenshotDto(
                                s.getId(),
                                s.getImageUrl(),
                                s.getAltText(),
                                s.getDisplayOrder()
                        ))
                        .sorted(Comparator.comparing(ProjectScreenshotDto::displayOrder))
                        .toList()
        );

        log.debug("Project DTO mapped successfully: {}", dto);

        return dto;
    }

}

