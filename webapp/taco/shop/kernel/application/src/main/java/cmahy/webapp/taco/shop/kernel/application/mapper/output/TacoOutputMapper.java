package cmahy.webapp.taco.shop.kernel.application.mapper.output;

import cmahy.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.webapp.taco.shop.kernel.domain.Taco;
import cmahy.webapp.taco.shop.kernel.domain.id.TacoId;
import cmahy.webapp.taco.shop.kernel.exception.RequiredException;
import cmahy.webapp.taco.shop.kernel.vo.output.IngredientOutputVo;
import cmahy.webapp.taco.shop.kernel.vo.output.TacoOutputVo;
import jakarta.inject.Named;

import java.util.*;

@Named
public class TacoOutputMapper {

    private final IngredientOutputMapper ingredientOutputMapper;

    public TacoOutputMapper(IngredientOutputMapper ingredientOutputMapper) {
        this.ingredientOutputMapper = ingredientOutputMapper;
    }

    public TacoOutputVo map(Taco taco) throws RequiredException {
        if (Objects.isNull(taco)) {
            throw new RequiredException(Taco.class);
        }

        ArrayList<IngredientOutputVo> ingredientsVo = new ArrayList<>(taco.getIngredients().size());

        for (Ingredient ingredient : taco.getIngredients()) {
            ingredientsVo.add(ingredientOutputMapper.map(ingredient));
        }

        return new TacoOutputVo(
            new TacoId(taco.getId()),
            taco.getCreatedAt(),
            taco.getName(),
            Collections.unmodifiableList(ingredientsVo)
        );
    }
}
