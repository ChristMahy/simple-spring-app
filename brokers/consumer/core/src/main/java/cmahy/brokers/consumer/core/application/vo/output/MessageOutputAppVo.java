package cmahy.brokers.consumer.core.application.vo.output;

import cmahy.brokers.consumer.core.application.vo.id.MessageAppId;

import java.time.LocalDateTime;

public record MessageOutputAppVo(
    MessageAppId id,
    LocalDateTime createdAt,
    String message
) { }
