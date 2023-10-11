package cmahy.brokers.consumer.core.application.vo.input;

import cmahy.brokers.consumer.core.application.vo.id.MessageAppId;

import java.time.LocalDateTime;

public record MessageInputEventAppVo(
    MessageAppId id,
    String message,
    LocalDateTime createdAt
) { }
