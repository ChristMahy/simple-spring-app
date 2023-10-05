package cmahy.brokers.publisher.event.vo.output;

import java.time.LocalDateTime;

public record MessageOutputEventVo(
    String message,
    LocalDateTime createdAt
) { }
