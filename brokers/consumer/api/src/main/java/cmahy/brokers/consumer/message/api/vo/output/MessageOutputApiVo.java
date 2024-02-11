package cmahy.brokers.consumer.message.api.vo.output;

import cmahy.brokers.consumer.message.api.vo.id.MessageApiId;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record MessageOutputApiVo(
    MessageApiId id,
    LocalDateTime createdAt,
    String message,
    Integer modificationCounter
) { }
