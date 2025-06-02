package cmahy.simple.spring.webapp.reactive.resource.impl.domain;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.UUID;

public record Todo(
    @Id UUID id,
    String title,
    String description,
    LocalDateTime createdAt
) {

    public Todo(String title, String description) {
        this(null, title, description, LocalDateTime.now());
    }
}
