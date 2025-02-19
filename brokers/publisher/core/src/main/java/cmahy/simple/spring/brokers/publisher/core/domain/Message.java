package cmahy.simple.spring.brokers.publisher.core.domain;

import java.time.LocalDateTime;

public record Message(
    Long id,
    LocalDateTime createdAt,
    String message
) {
    public Message(String message) {
        this(null, LocalDateTime.now(), message);
    }
}
