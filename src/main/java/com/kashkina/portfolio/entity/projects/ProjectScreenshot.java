package com.kashkina.portfolio.entity.projects;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "project_screenshots")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectScreenshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "image_url", length = 500, nullable = false)
    private String imageUrl;

    @Column(name = "alt_text", length = 255, nullable = true)
    private String altText;

    @Column(name = "display_order", nullable = false)
    private Integer displayOrder;

    // --- relation with Project ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
}

