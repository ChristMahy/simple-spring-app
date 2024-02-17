package cmahy.webapp.resource.impl.application.taco.shop.mapper.output;

import cmahy.webapp.resource.impl.domain.taco.id.IngredientId;
import cmahy.webapp.resource.impl.application.taco.shop.vo.output.IngredientOutputAppVo;
import cmahy.webapp.resource.impl.domain.taco.Ingredient;
import jakarta.inject.Named;

@Named
public class IngredientOutputMapper {

    public IngredientOutputAppVo map(Ingredient ingredient) {
        if (ingredient == null) {
            return null;
        }

        return new IngredientOutputAppVo(
            new IngredientId(ingredient.getId()),
            ingredient.getName(),
            ingredient.getType().name()
        );
    }
}
