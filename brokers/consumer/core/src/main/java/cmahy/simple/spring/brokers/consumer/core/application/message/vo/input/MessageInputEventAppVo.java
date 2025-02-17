package cmahy.simple.spring.brokers.consumer.core.application.message.vo.input;

import cmahy.simple.spring.brokers.consumer.core.application.message.vo.id.MessageAppId;

import java.time.LocalDateTime;

public record MessageInputEventAppVo(
    MessageAppId id,
    String message,
    LocalDateTime createdAt
) { }
