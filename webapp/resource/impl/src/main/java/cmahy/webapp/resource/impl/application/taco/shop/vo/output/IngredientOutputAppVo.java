package cmahy.webapp.resource.impl.application.taco.shop.vo.output;

import cmahy.webapp.resource.impl.application.taco.shop.vo.id.IngredientIdVo;

public record IngredientOutputAppVo(
    IngredientIdVo id,
    String name,
    String type
) {
}
