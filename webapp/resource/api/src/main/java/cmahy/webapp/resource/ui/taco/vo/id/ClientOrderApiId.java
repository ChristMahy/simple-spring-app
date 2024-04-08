package cmahy.webapp.resource.ui.taco.vo.id;

import cmahy.common.entity.id.EntityId;

public record ClientOrderApiId(
    Long value
) implements EntityId<Long> {
}
