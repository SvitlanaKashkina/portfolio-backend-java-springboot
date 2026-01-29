package com.kashkina.portfolio.entity.projects;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "projects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Integer id;

        @Column(name = "title", nullable = false)
        private String title;

        @Column(name = "short_description", nullable = false, columnDefinition = "TEXT")
        private String shortDescription;

        @Column(name = "full_description", columnDefinition = "TEXT")
        private String fullDescription;

        @Column(name = "architecture_description", columnDefinition = "TEXT")
        private String architectureDescription;

        @Column(name = "role_description", columnDefinition = "TEXT")
        private String roleDescription;

        @Column(name = "lessons_learned", columnDefinition = "TEXT")
        private String lessonsLearned;

        @Column(name = "status", columnDefinition = "TEXT")
        private String status;

        @Column(name = "end_date")
        private LocalDate endDate;

        @Column(name = "github_url", columnDefinition = "TEXT")
        private String githubUrl;

        @CreationTimestamp
        @Column(name = "created_at", nullable = false, updatable = false)
        private LocalDateTime createdAt;

        @UpdateTimestamp
        @Column(name = "updated_at", nullable = false)
        private LocalDateTime updatedAt;

        // --- relations ---
        @ManyToMany
        @JoinTable(
                name = "project_technologies",
                joinColumns = @JoinColumn(name = "project_id", nullable = false),
                inverseJoinColumns = @JoinColumn(name = "technology_id", nullable = false)
        )
        private Set<Technology> technologies = new HashSet<>();

        @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
        private Set<ProjectFeature> features = new HashSet<>();

        @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
        @OrderBy("displayOrder ASC")
        private Set<ProjectScreenshot> screenshots = new HashSet<>();
}
