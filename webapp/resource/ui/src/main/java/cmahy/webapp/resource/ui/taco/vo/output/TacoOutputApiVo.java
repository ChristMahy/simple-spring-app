package cmahy.webapp.resource.ui.taco.vo.output;

import cmahy.webapp.resource.ui.taco.vo.id.TacoApiId;

import java.util.Date;
import java.util.List;

public record TacoOutputApiVo(
    TacoApiId id,
    Date createdAt,
    String name,
    List<IngredientOutputApiVo> ingredients
) {
}
