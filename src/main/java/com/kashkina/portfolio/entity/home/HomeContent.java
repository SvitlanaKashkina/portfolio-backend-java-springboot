package com.kashkina.portfolio.entity.home;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "home_content")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HomeContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "full_name", length = 255, nullable = false)
    private String fullName;

    @Column(name = "role_title", length = 255, nullable = false)
    private String roleTitle;

    @Column(name = "role_type", length = 255)
    private String roleType;

    @Column(name = "short_bio", columnDefinition = "TEXT")
    private String shortBio;

    @Column(name = "github_url", length = 255)
    private String githubUrl;

    @Column(name = "linkedin_url", length = 255)
    private String linkedinUrl;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();
}