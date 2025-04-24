package com.example.semanticversion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
public class SemanticVersionApplication {

    @Value("${spring.application.version}")
    private String version;

    public static void main(String[] args) {
        SpringApplication.run(SemanticVersionApplication.class, args);
    }

    @GetMapping("/")
    public String home() {
        return "Semantic Version App is running!";
    }

    @GetMapping("/version")
    public Map<String, String> version() {
        Map<String, String> versionInfo = new HashMap<>();
        versionInfo.put("version", version);
        return versionInfo;
    }

    @GetMapping("/health")
    public String health() {
        return "Application is healthy!";
    }

    @GetMapping("/system")
    public Map<String, String> system() {
        Map<String, String> info = new HashMap<>();
        info.put("version", version);
        info.put("java.version", System.getProperty("java.version"));
        info.put("os.name", System.getProperty("os.name"));
        return info;
    }

    @GetMapping("/metrics")
    public Map<String, Object> metrics() {
        Map<String, Object> metrics = new HashMap<>();
        metrics.put("memory.free", Runtime.getRuntime().freeMemory());
        metrics.put("memory.total", Runtime.getRuntime().totalMemory());
        metrics.put("memory.max", Runtime.getRuntime().maxMemory());
        metrics.put("processors", Runtime.getRuntime().availableProcessors());
        return metrics;
    }
}