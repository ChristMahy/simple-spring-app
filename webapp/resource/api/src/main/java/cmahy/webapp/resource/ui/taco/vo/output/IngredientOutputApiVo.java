package cmahy.webapp.resource.ui.taco.vo.output;

import cmahy.webapp.resource.ui.taco.vo.id.IngredientApiId;

public record IngredientOutputApiVo(
    IngredientApiId id,
    String name,
    String type
) {
}
