package cmahy.brokers.consumer.core.application.message.vo.output;

import cmahy.brokers.consumer.core.application.message.vo.id.MessageAppId;

import java.time.LocalDateTime;

public record MessageOutputAppVo(
    MessageAppId id,
    LocalDateTime createdAt,
    String message,
    Integer modificationCounter
) { }
