package cmahy.webapp.resource.impl.domain.user.id;

import cmahy.common.entity.id.EntityId;

public record UserId(
    Long value
) implements EntityId<Long> {}
