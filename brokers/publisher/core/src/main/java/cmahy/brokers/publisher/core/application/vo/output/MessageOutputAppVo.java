package cmahy.brokers.publisher.core.application.vo.output;

import cmahy.brokers.publisher.core.application.vo.id.MessageAppId;

import java.time.LocalDateTime;

public record MessageOutputAppVo(
    MessageAppId id,
    LocalDateTime createdAt,
    String message
) {
}
