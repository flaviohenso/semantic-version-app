package com.example.semanticversion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SemanticVersionApplication {

    public static void main(String[] args) {
        SpringApplication.run(SemanticVersionApplication.class, args);
    }

    @GetMapping("/version")
    public String version() {
        return "Semantic Version App - v1.0.0";
    }

    @GetMapping("/health")
    public String health() {
        return "Aplicação está funcionando normalmente!";
    }
}