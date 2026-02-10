package com.kashkina.portfolio.kafka.producer;

import com.kashkina.portfolio.kafka.event.VisitEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;

@Component
@RequiredArgsConstructor
public class VisitEventProducer {
    private static final Logger log = LoggerFactory.getLogger(VisitEventProducer.class);

    private final KafkaTemplate<String, VisitEvent> kafkaTemplate;
    private final String TOPIC = "visit-events";

    @Async("taskExecutor")
    public void sendVisitEventAsync(VisitEvent event) {

        try {
            kafkaTemplate.send(TOPIC, event.getSessionId(), event);
            log.info("Kafka send succeeded for {}", event.getSessionId());
        } catch (Exception e) {
            log.error("Kafka send failed for {}: {}", event.getSessionId(), e.getMessage(), e);
        }
    }
}