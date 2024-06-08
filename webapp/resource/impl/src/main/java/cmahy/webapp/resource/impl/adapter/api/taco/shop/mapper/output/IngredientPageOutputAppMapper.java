package cmahy.webapp.resource.impl.adapter.api.taco.shop.mapper.output;

import cmahy.webapp.resource.api.taco.shop.vo.output.IngredientPageOutputApiVo;
import cmahy.webapp.resource.impl.application.taco.shop.vo.output.IngredientPageOutputAppVo;
import cmahy.webapp.resource.impl.exception.NullException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class IngredientPageOutputAppMapper {

    private final IngredientOutputAppMapper ingredientOutputAppMapper;

    public IngredientPageOutputAppMapper(IngredientOutputAppMapper ingredientOutputAppMapper) {
        this.ingredientOutputAppMapper = ingredientOutputAppMapper;
    }

    public IngredientPageOutputApiVo map(IngredientPageOutputAppVo output) {

        if (Objects.isNull(output)) {
            throw new NullException(IngredientPageOutputAppVo.class);
        }

        return new IngredientPageOutputApiVo(
            output.content().stream().map(ingredientOutputAppMapper::map).toList(),
            output.totalElements()
        );
    }
}
