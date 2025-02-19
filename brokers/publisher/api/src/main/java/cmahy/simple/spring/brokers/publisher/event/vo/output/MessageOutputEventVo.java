package cmahy.simple.spring.brokers.publisher.event.vo.output;

import cmahy.simple.spring.brokers.publisher.event.vo.id.MessageEventId;

import java.time.LocalDateTime;

public record MessageOutputEventVo(
    MessageEventId id,
    String message,
    LocalDateTime createdAt
) { }
