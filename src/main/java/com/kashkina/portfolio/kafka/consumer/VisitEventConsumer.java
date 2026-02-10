package com.kashkina.portfolio.kafka.consumer;

import com.kashkina.portfolio.kafka.event.VisitEvent;
import com.kashkina.portfolio.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
@Component
@RequiredArgsConstructor
public class VisitEventConsumer {

    private final EmailService emailService;
    private final RedisTemplate<String, Object> redisTemplate;

    // In-memory (lives while the application is running)
    private final Map<String, Integer> pageVisits = new ConcurrentHashMap<>();
    private final Map<String, Set<String>> pageUniqueSessions = new ConcurrentHashMap<>();
    private final Set<String> totalUniqueSessions = ConcurrentHashMap.newKeySet();

    private static final Duration TTL_30_DAYS = Duration.ofDays(30);

    @KafkaListener(
            topics = "visit-events",
            groupId = "visit-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(VisitEvent event) {

        log.info("📩 Received visit event from Kafka: {}", event);

        String page = event.getPage();
        String sessionId = event.getSessionId();

        if (page == null || sessionId == null) {
            log.warn("⚠️ Invalid visit event received (page or sessionId is null): {}", event);
            return;
        }

        // --- Total page visits ---
        int totalVisits = pageVisits.merge(page, 1, Integer::sum);
        log.debug("📊 Page '{}' total visits updated to {}", page, totalVisits);

        // --- Unique page visitors ---
        pageUniqueSessions
                .computeIfAbsent(page, k -> ConcurrentHashMap.newKeySet())
                .add(sessionId);

        int uniquePageVisitors = pageUniqueSessions.get(page).size();
        log.debug("👥 Page '{}' unique visitors count: {}", page, uniquePageVisitors);

        // --- Unique site visitors ---
        boolean isNewVisitor = totalUniqueSessions.add(sessionId);

        if (isNewVisitor) {
            log.info("🆕 New unique site visitor detected: sessionId={}", sessionId);

            try {
                emailService.sendVisitNotification(
                        event,
                        totalUniqueSessions.size(),
                        pageVisits
                );
                log.info("📧 Visit notification email successfully sent");
            } catch (Exception e) {
                log.error("❌ Failed to send visit notification email", e);
            }
        }

        // --- Persist statistics to Redis ---
        try {
            saveStatisticsToRedis();
            log.info("✅ Visit statistics successfully saved to Redis");
        } catch (Exception e) {
            log.error("❌ Failed to save visit statistics to Redis", e);
        }

        // --- Log current statistics ---
        logStatistics();
    }

    private void saveStatisticsToRedis() {

        log.debug("💾 Saving site statistics to Redis");

        // Total unique site visitors
        redisTemplate.opsForValue()
                .set("site:uniqueVisitors", totalUniqueSessions.size(), TTL_30_DAYS);

        log.debug("🧮 Redis key 'site:uniqueVisitors' set to {}", totalUniqueSessions.size());

        for (String page : pageVisits.keySet()) {

            String totalKey = "page:" + page + ":totalVisits";
            String uniqueKey = "page:" + page + ":uniqueVisitors";

            // Total page visits
            redisTemplate.opsForValue()
                    .set(totalKey, pageVisits.get(page), TTL_30_DAYS);

            log.debug("🧮 Redis key '{}' set to {}", totalKey, pageVisits.get(page));

            // Replace unique visitors set
            redisTemplate.delete(uniqueKey);
            redisTemplate.opsForSet()
                    .add(uniqueKey, pageUniqueSessions.get(page).toArray());
            redisTemplate.expire(uniqueKey, TTL_30_DAYS);

            log.debug(
                    "👥 Redis key '{}' updated ({} unique visitors, TTL {} days)",
                    uniqueKey,
                    pageUniqueSessions.get(page).size(),
                    TTL_30_DAYS.toDays()
            );
        }
    }

    private void logStatistics() {

        StringBuilder sb = new StringBuilder();
        sb.append("\n=== Current Site Visit Statistics ===\n");
        sb.append("Total unique site visitors: ")
                .append(totalUniqueSessions.size())
                .append("\n\n");

        pageVisits.forEach((page, total) -> {
            int unique = pageUniqueSessions
                    .getOrDefault(page, Set.of())
                    .size();

            sb.append(page)
                    .append(" -> total visits: ")
                    .append(total)
                    .append(", unique visitors: ")
                    .append(unique)
                    .append("\n");
        });

        log.info(sb.toString());
    }
}
