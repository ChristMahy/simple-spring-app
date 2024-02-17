package cmahy.webapp.resource.impl.application.taco.shop.vo.output;

import cmahy.webapp.resource.impl.domain.taco.id.TacoId;

import java.util.Date;
import java.util.List;

public record TacoOutputAppVo(
    TacoId id,
    Date createdAt,
    String name,
    List<IngredientOutputAppVo> ingredients
) {
}
