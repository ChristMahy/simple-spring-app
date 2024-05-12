package cmahy.webapp.resource.ui.taco.vo.output;

import cmahy.webapp.resource.ui.taco.vo.id.IngredientUiId;

public record IngredientOutputUiVo(
    IngredientUiId id,
    String name,
    String type
) {
}
