package cmahy.webapp.resource.impl.application.taco.shop.vo.output;

import cmahy.webapp.resource.impl.domain.taco.id.IngredientId;

public record IngredientOutputAppVo(
    IngredientId id,
    String name,
    String type
) {
}
