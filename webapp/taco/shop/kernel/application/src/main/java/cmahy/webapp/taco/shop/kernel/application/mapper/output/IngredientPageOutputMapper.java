package cmahy.webapp.taco.shop.kernel.application.mapper.output;

import cmahy.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.webapp.taco.shop.kernel.domain.page.IngredientPage;
import cmahy.webapp.taco.shop.kernel.exception.RequiredException;
import cmahy.webapp.taco.shop.kernel.vo.output.IngredientOutputVo;
import cmahy.webapp.taco.shop.kernel.vo.output.IngredientPageOutputVo;
import jakarta.inject.Named;

import java.util.*;

@Named
public class IngredientPageOutputMapper {

    private final IngredientOutputMapper ingredientOutputMapper;

    public IngredientPageOutputMapper(IngredientOutputMapper ingredientOutputMapper) {
        this.ingredientOutputMapper = ingredientOutputMapper;
    }

    public IngredientPageOutputVo map(IngredientPage source) throws RequiredException {
        if (Objects.isNull(source)) {
            throw new RequiredException(IngredientPage.class);
        }

        ArrayList<IngredientOutputVo> ingredientsOutputVo = new ArrayList<>(source.content().size());

        for (Ingredient ingredient : source.content()) {
            ingredientsOutputVo.add(ingredientOutputMapper.map(ingredient));
        }

        return new IngredientPageOutputVo(
            Collections.unmodifiableList(ingredientsOutputVo),
            source.totalElements()
        );
    }
}
