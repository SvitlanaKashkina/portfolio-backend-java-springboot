package com.kashkina.portfolio.dto.skills;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TechnicalSkillItemDTO {

    private Integer id;
    private String description;
    private Integer orderIndex;
    private Integer categoryId;
}
