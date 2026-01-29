package com.kashkina.portfolio.entity.skills;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "soft_skill")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SoftSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(name = "order_index", nullable = false)
    private Integer orderIndex;
}