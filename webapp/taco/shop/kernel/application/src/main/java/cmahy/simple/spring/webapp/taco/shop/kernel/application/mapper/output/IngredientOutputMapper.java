package cmahy.simple.spring.webapp.taco.shop.kernel.application.mapper.output;

import cmahy.simple.spring.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.id.IngredientId;
import cmahy.simple.spring.webapp.taco.shop.kernel.exception.RequiredException;
import cmahy.simple.spring.webapp.taco.shop.kernel.vo.output.IngredientOutputVo;
import jakarta.inject.Named;

import java.util.Objects;

@Named
public class IngredientOutputMapper {

    public IngredientOutputVo map(Ingredient ingredient) throws RequiredException {
        if (Objects.isNull(ingredient)) {
            throw new RequiredException(Ingredient.class);
        }

        return new IngredientOutputVo(
            new IngredientId(ingredient.getId()),
            ingredient.getName(),
            ingredient.getType().name()
        );
    }
}
