package com.kashkina.portfolio.controller;

import com.kashkina.portfolio.dto.about.AboutMeResponseDTO;
import com.kashkina.portfolio.service.AboutMeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/about")
@RequiredArgsConstructor
public class AboutMeController {

    private static final Logger log = LoggerFactory.getLogger(AboutMeController.class);

    private final AboutMeService service;

    @GetMapping
    public AboutMeResponseDTO getAboutMe() {

        log.info("GET /api/about called");

        return service.getAboutMeContent();
    }
}
