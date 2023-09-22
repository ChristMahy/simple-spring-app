package cmahy.brokers.consumer.vo.output;

import cmahy.brokers.consumer.vo.id.MessageApiId;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.time.LocalDateTime;

public record MessageOutputApiVo(
    @JsonUnwrapped
    MessageApiId id,
    LocalDateTime createdAt,
    String message
) { }
