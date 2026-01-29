package com.kashkina.portfolio.kafka.producer;

import com.kashkina.portfolio.kafka.event.VisitEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@RequiredArgsConstructor
public class VisitEventProducer {

    private final KafkaTemplate<String, VisitEvent> kafkaTemplate;
    private static final String TOPIC = "visit-events";

    public void sendVisitEvent(VisitEvent event) {
        kafkaTemplate.send(TOPIC, event.getSessionId(), event);
    }
}