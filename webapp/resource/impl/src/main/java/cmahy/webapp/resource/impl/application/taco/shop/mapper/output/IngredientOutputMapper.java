package cmahy.webapp.resource.impl.application.taco.shop.mapper.output;

import cmahy.webapp.resource.impl.domain.taco.Ingredient;
import cmahy.webapp.resource.impl.exception.NullException;
import cmahy.webapp.resource.taco.shop.id.IngredientId;
import cmahy.webapp.resource.taco.shop.vo.output.IngredientOutputVo;
import jakarta.inject.Named;

import java.util.Objects;

@Named
public class IngredientOutputMapper {

    public IngredientOutputVo map(Ingredient ingredient) {
        if (Objects.isNull(ingredient)) {
            throw new NullException(Ingredient.class);
        }

        return new IngredientOutputVo(
            new IngredientId(ingredient.getId()),
            ingredient.getName(),
            ingredient.getType().name()
        );
    }
}
