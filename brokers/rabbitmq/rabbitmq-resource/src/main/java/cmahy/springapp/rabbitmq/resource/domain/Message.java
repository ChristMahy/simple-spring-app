package cmahy.springapp.rabbitmq.resource.domain;

import java.time.LocalDateTime;

public record Message(
    LocalDateTime createdAt,
    String message
) {
    public Message(String message) {
        this(LocalDateTime.now(), message);
    }
}
