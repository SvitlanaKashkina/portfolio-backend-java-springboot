package com.kashkina.portfolio.controller;

import com.kashkina.portfolio.dto.about.AboutMeResponseDTO;
import com.kashkina.portfolio.kafka.event.VisitEvent;
import com.kashkina.portfolio.kafka.producer.VisitEventProducer;
import com.kashkina.portfolio.service.AboutMeService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/about")
@RequiredArgsConstructor
public class AboutMeController {

    private static final Logger log = LoggerFactory.getLogger(AboutMeController.class);

    private final AboutMeService service;
    private final VisitEventProducer visitEventProducer;

    @GetMapping
    public AboutMeResponseDTO getAboutMe(HttpSession session) {
        log.info("GET /API/ABOUT CALLED");

        AboutMeResponseDTO response = service.getAboutMeContent();

        // Sending an event to Kafka
        VisitEvent event = new VisitEvent(
                session.getId(),
                "/api/about",
                LocalDateTime.now()
        );

        visitEventProducer.sendVisitEventAsync(event);

        return response;
    }
}
