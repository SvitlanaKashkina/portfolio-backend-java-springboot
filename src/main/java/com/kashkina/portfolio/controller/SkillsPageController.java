package com.kashkina.portfolio.controller;

import com.kashkina.portfolio.dto.skills.SkillsPageDTO;
import com.kashkina.portfolio.kafka.event.VisitEvent;
import com.kashkina.portfolio.kafka.producer.VisitEventProducer;
import com.kashkina.portfolio.service.SkillsPageService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


@RestController
@RequestMapping("/api/skills")
@RequiredArgsConstructor
public class SkillsPageController {

    private static final Logger log = LoggerFactory.getLogger(SkillsPageController.class);

    private final SkillsPageService skillsPageService;
    private final VisitEventProducer visitEventProducer; // Kafka Producer

    @GetMapping
    public SkillsPageDTO getSkillsPage(HttpSession session) {
        log.info("GET /api/skills called");

        // Send an event to Kafka on every page visit
        visitEventProducer.sendVisitEvent(
                new VisitEvent(
                        session.getId(),           // unique session
                        "/api/skills",
                        LocalDateTime.now()
                )
        );

        SkillsPageDTO skillsPage = skillsPageService.getAllSkills();

        if (skillsPage.getTechStack().isEmpty() &&
                skillsPage.getTechnicalSkills().isEmpty() &&
                skillsPage.getSoftSkills().isEmpty()) {
            log.warn("No skills found in database");
        } else {
            log.info("Returning skills page successfully");
            log.debug("SkillsPageDTO details: {}", skillsPage);
        }

        return skillsPage;
    }
}

