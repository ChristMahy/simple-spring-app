package cmahy.simple.spring.webapp.reactive.resource.api.todo.vo.id;

import cmahy.simple.spring.common.entity.id.EntityId;

import java.util.UUID;

public record TodoApiId(UUID value) implements EntityId<UUID> {

    public TodoApiId(String value) {
        this(UUID.fromString(value));
    }

}
