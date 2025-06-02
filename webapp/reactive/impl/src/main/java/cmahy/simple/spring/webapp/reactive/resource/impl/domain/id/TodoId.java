package cmahy.simple.spring.webapp.reactive.resource.impl.domain.id;

import cmahy.simple.spring.common.entity.id.EntityId;

import java.util.UUID;

public record TodoId(UUID value) implements EntityId<UUID> {
}
