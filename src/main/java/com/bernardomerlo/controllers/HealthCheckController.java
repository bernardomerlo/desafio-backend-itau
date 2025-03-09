package com.bernardomerlo.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/health")
public class HealthCheckController {

    private static final LocalDateTime START_TIME = LocalDateTime.now();

    @Value("${spring.profiles.active:default}")
    private String environment;

    @GetMapping
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("uptime", formatDuration(Duration.between(START_TIME, LocalDateTime.now())));
        response.put("environment", environment);

        return ResponseEntity.ok(response);
    }

    private String formatDuration(Duration duration) {
        long days = duration.toDays();
        long hours = duration.minusDays(days).toHours();
        long minutes = duration.minusDays(days).minusHours(hours).toMinutes();
        long seconds = duration.minusDays(days).minusHours(hours).minusMinutes(minutes).getSeconds();
        return String.format("%d days %d hours %d minutes %d seconds", days, hours, minutes, seconds);

    }
}
