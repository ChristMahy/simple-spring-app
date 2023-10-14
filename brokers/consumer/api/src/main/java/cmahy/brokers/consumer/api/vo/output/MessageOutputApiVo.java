package cmahy.brokers.consumer.api.vo.output;

import cmahy.brokers.consumer.api.vo.id.MessageApiId;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.time.LocalDateTime;

public record MessageOutputApiVo(
    @JsonUnwrapped
    MessageApiId id,
    LocalDateTime createdAt,
    String message,
    Integer modificationCounter
) { }
