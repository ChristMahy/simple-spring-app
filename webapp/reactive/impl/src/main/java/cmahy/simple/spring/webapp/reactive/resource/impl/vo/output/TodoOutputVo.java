package cmahy.simple.spring.webapp.reactive.resource.impl.vo.output;

import cmahy.simple.spring.webapp.reactive.resource.impl.domain.id.TodoId;

import java.time.LocalDateTime;

public record TodoOutputVo(
    TodoId id,
    String title,
    String description,
    LocalDateTime createdAt
) {
}
