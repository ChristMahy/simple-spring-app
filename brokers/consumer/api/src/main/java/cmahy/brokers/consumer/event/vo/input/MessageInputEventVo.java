package cmahy.brokers.consumer.event.vo.input;

import cmahy.brokers.consumer.event.vo.id.MessageEventId;

import java.time.LocalDateTime;

public record MessageInputEventVo(
    MessageEventId id,
    String message,
    LocalDateTime createdAt
) { }
