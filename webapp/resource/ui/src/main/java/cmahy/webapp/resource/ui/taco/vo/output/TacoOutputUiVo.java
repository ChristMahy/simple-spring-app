package cmahy.webapp.resource.ui.taco.vo.output;

import cmahy.webapp.resource.ui.taco.vo.id.TacoUiId;

import java.util.Date;
import java.util.List;

public record TacoOutputUiVo(
    TacoUiId id,
    Date createdAt,
    String name,
    List<IngredientOutputUiVo> ingredients
) {
}
