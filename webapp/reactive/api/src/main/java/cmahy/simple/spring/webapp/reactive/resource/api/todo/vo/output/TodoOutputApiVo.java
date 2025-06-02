package cmahy.simple.spring.webapp.reactive.resource.api.todo.vo.output;

import cmahy.simple.spring.webapp.reactive.resource.api.todo.vo.id.TodoApiId;

import java.time.LocalDateTime;

public record TodoOutputApiVo(
    TodoApiId id,
    String title,
    String description,
    LocalDateTime createdAt
) {
}
