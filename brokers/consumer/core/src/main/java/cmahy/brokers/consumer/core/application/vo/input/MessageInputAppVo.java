package cmahy.brokers.consumer.core.application.vo.input;

import java.time.LocalDateTime;

public record MessageInputAppVo(
    String message,
    LocalDateTime createdAt
) { }
