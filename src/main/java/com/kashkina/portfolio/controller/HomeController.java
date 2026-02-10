package com.kashkina.portfolio.controller;

import com.kashkina.portfolio.dto.home.HomeContentDTO;
import com.kashkina.portfolio.entity.home.HomeContent;
import com.kashkina.portfolio.kafka.event.VisitEvent;
import com.kashkina.portfolio.kafka.producer.VisitEventProducer;
import com.kashkina.portfolio.service.HomeContentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class HomeController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    private final HomeContentService homeContentService;
    private final VisitEventProducer visitEventProducer; // Kafka Producer

    @GetMapping("/home")
    public HomeContentDTO getHomeContent(HttpSession session) {
        log.info("GET /API/HOME CALLED");

        HomeContentDTO contentDto = homeContentService.getHomeContentDTO();

        log.info("HomeContentDTO FETCHED SUCCESSFULLY");

        // Sending an event to Kafka
        VisitEvent event = new VisitEvent(
                session.getId(),
                "/api/home",
                LocalDateTime.now()
        );
        visitEventProducer.sendVisitEventAsync(event);

        return contentDto;
    }
}
