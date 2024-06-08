package cmahy.webapp.resource.impl.adapter.api.taco.shop.mapper.output;

import cmahy.webapp.resource.api.taco.shop.vo.output.IngredientOutputApiVo;
import cmahy.webapp.resource.impl.application.taco.shop.vo.output.IngredientOutputAppVo;
import cmahy.webapp.resource.impl.exception.NullException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class IngredientOutputAppMapper {

    private final IngredientIdMapper ingredientIdMapper;

    public IngredientOutputAppMapper(IngredientIdMapper ingredientIdMapper) {
        this.ingredientIdMapper = ingredientIdMapper;
    }

    public IngredientOutputApiVo map(IngredientOutputAppVo output) {

        if (Objects.isNull(output)) {
            throw new NullException(IngredientOutputAppVo.class);
        }

        return new IngredientOutputApiVo(
            ingredientIdMapper.map(output.id()),
            output.name(),
            output.type()
        );
    }
}
