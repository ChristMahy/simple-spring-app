package cmahy.brokers.consumer.event.vo.input;

import cmahy.brokers.consumer.event.vo.id.MessageEventId;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.time.LocalDateTime;

public record MessageInputEventVo(
    @JsonUnwrapped
    MessageEventId id,
    String message,
    LocalDateTime createdAt
) {
}
