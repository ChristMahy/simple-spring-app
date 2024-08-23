package cmahy.webapp.user.kernel.domain.id;

import cmahy.common.entity.id.EntityId;

public record UserId(
    Long value
) implements EntityId<Long> {}
