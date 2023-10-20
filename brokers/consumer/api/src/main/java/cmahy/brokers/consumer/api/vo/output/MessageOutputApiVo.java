package cmahy.brokers.consumer.api.vo.output;

import cmahy.brokers.consumer.api.vo.id.MessageApiId;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record MessageOutputApiVo(
    MessageApiId id,
    LocalDateTime createdAt,
    String message,
    Integer modificationCounter
) { }
