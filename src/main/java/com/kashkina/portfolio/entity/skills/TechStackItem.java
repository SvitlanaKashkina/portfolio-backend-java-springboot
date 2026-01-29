package com.kashkina.portfolio.entity.skills;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tech_stack_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TechStackItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "order_index")
    private Integer orderIndex;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private TechStackCategory category;
}