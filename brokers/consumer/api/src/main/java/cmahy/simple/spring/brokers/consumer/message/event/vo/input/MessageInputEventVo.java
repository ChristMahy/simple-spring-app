package cmahy.simple.spring.brokers.consumer.message.event.vo.input;

import cmahy.simple.spring.brokers.consumer.message.event.vo.id.MessageEventId;

import java.time.LocalDateTime;

public record MessageInputEventVo(
    MessageEventId id,
    String message,
    LocalDateTime createdAt
) { }
