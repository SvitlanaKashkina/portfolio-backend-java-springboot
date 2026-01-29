package com.kashkina.portfolio.kafka.consumer;

import com.kashkina.portfolio.kafka.event.VisitEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
public class VisitEventConsumer {

    // Visit counters
    private final ConcurrentHashMap<String, AtomicInteger> pageVisits = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Boolean> sessionTracker = new ConcurrentHashMap<>();
    private final AtomicInteger totalSessions = new AtomicInteger(0);

    @KafkaListener(topics = "visit-events", groupId = "visit-group")
    public void consume(VisitEvent event) {
        log.info("Received visit event: {}", event);

        // Total number of sessions
        sessionTracker.putIfAbsent(event.getSessionId(), true);
        totalSessions.set(sessionTracker.size());

        // Page counter
        pageVisits.compute(event.getPage(), (key, value) -> {
            if (value == null) return new AtomicInteger(1);
            value.incrementAndGet();
            return value;
        });

        log.info("Total sessions: {}", totalSessions.get());
        log.info("Page '{}' visits: {}", event.getPage(), pageVisits.get(event.getPage()).get());
    }

    // Methods for accessing counters
    public int getTotalSessions() {
        return totalSessions.get();
    }

    public int getPageVisits(String page) {
        return pageVisits.getOrDefault(page, new AtomicInteger(0)).get();
    }
}
