package com.example.semanticversion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SemanticVersionApplication {

    @Value("${spring.application.version:1.0.0}")
    private String version;

    public static void main(String[] args) {
        SpringApplication.run(SemanticVersionApplication.class, args);
    }

    @GetMapping("/version")
    public String version() {
        return String.format("Semantic Version App - v%s", version);
    }

    @GetMapping("/health")
    public String health() {
        return "Aplicação está funcionando normalmente!";
    }
}