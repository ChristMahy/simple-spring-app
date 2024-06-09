package cmahy.webapp.resource.impl.application.taco.shop.mapper.output;

import cmahy.webapp.resource.impl.domain.taco.page.IngredientPage;
import cmahy.webapp.resource.impl.exception.NullException;
import cmahy.webapp.resource.taco.shop.vo.output.IngredientPageOutputVo;
import jakarta.inject.Named;

import java.util.Objects;

@Named
public class IngredientPageOutputMapper {

    private final IngredientOutputMapper ingredientOutputMapper;

    public IngredientPageOutputMapper(IngredientOutputMapper ingredientOutputMapper) {
        this.ingredientOutputMapper = ingredientOutputMapper;
    }

    public IngredientPageOutputVo map(IngredientPage source) {
        if (Objects.isNull(source)) {
            throw new NullException(IngredientPage.class);
        }

        return new IngredientPageOutputVo(
            source.content().stream().map(ingredientOutputMapper::map).toList(),
            source.totalElements()
        );
    }
}
