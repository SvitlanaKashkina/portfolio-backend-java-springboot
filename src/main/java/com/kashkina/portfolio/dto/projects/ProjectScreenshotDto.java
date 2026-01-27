package com.kashkina.portfolio.dto.projects;

public record ProjectScreenshotDto(
        Long id,
        String imageUrl,
        String altText,
        Integer displayOrder
) {

}
