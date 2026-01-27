package com.kashkina.portfolio.dto.projects;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectDto {

    private Long id;
    private String title;
    private String shortDescription;
    private String fullDescription;
    private String architectureDescription;
    private String roleDescription;
    private String lessonsLearned;
    private String status;
    private LocalDate endDate;
    private String githubUrl;

    private List<TechnologyDto> technologies;
    private List<ProjectFeatureDto> features;
    private List<ProjectScreenshotDto> screenshots;
}
