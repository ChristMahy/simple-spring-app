package cmahy.brokers.consumer.core.domain.message;

import java.time.LocalDateTime;

public record Message(
    Long id,
    LocalDateTime createdAt,
    String message,
    Integer modificationCounter
) {
    public Message(String message) {
        this(null, LocalDateTime.now(), message, 0);
    }
}
