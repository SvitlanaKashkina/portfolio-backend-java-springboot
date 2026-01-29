package com.kashkina.portfolio.entity.skills;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "technical_skill_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TechnicalSkillItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String description;

    @Column(name = "order_index", nullable = false)
    private Integer orderIndex;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private TechnicalSkillCategory category;
}