package com.kashkina.portfolio.controller;

import com.kashkina.portfolio.entity.home.HomeContent;
import com.kashkina.portfolio.kafka.event.VisitEvent;
import com.kashkina.portfolio.kafka.producer.VisitEventProducer;
import com.kashkina.portfolio.repository.home.HomeContentRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class HomeContentController {

    private static final Logger log = LoggerFactory.getLogger(HomeContentController.class);

    private final HomeContentRepository repository;
    private final VisitEventProducer visitEventProducer; // Kafka Producer

    @GetMapping("/home")
    public HomeContent getHomeContent(HttpSession session) {
        log.info("GET /api/home called");

        // Sending an event to Kafka
        visitEventProducer.sendVisitEvent(
                new VisitEvent(
                        session.getId(),           // unique session
                        "/api/home",
                        LocalDateTime.now()
                )
        );

        HomeContent content = repository.findById(1)
                .orElseThrow(() -> {
                    log.error("Home content not found in database");
                    return new RuntimeException("Home content not found");
                });

        log.info("Returning Home content successfully");
        log.debug("Home content: {}", content);

        return content;
    }
}
