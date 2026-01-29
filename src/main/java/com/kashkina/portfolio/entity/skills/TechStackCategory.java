package com.kashkina.portfolio.entity.skills;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "tech_stack_category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TechStackCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(name = "order_index", nullable = false)
    private Integer orderIndex;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TechStackItem> items;
}