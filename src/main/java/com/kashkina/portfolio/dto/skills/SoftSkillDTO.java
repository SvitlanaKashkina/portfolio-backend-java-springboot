package com.kashkina.portfolio.dto.skills;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SoftSkillDTO {
    private Integer id;
    private String name;
    private Integer orderIndex;
}
