package com.kashkina.portfolio.dto.projects;

public record ProjectScreenshotDto(
        Integer id,
        String imageUrl,
        String altText,
        Integer displayOrder
) {

}
