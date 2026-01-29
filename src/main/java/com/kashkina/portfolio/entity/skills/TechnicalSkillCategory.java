package com.kashkina.portfolio.entity.skills;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "technical_skill_category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TechnicalSkillCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(name = "order_index", nullable = false)
    private Integer orderIndex;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TechnicalSkillItem> items;
}
