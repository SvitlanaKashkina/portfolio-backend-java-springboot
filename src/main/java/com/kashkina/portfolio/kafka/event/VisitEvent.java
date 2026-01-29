package com.kashkina.portfolio.kafka.event;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VisitEvent {
    private String sessionId;
    private String page;
    private LocalDateTime timestamp;
}
