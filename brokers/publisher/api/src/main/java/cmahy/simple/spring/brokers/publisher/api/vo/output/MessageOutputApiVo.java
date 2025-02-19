package cmahy.simple.spring.brokers.publisher.api.vo.output;

import cmahy.simple.spring.brokers.publisher.api.vo.id.MessageApiId;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.time.LocalDateTime;

public record MessageOutputApiVo(
    @JsonUnwrapped
    MessageApiId id,
    LocalDateTime createdAt,
    String message
) {
}
