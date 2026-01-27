package com.kashkina.portfolio.controller;

import com.kashkina.portfolio.entity.home.HomeContent;
import com.kashkina.portfolio.repository.home.HomeContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class HomeContentController {

    private static final Logger log = LoggerFactory.getLogger(HomeContentController.class);

    private final HomeContentRepository repository;

    @GetMapping("/home")
    public HomeContent getHomeContent() {
        log.info("GET /home called");

        HomeContent content = repository.findById(1L)
                .orElseThrow(() -> {
                    log.error("Home content not found in database");
                    return new RuntimeException("Home content not found");
                });

        log.info("Returning Home content successfully");
        log.debug("Home content: {}", content);

        return content;
    }
}

