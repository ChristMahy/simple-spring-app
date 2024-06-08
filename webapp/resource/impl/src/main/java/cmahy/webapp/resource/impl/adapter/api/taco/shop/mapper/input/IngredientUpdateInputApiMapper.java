package cmahy.webapp.resource.impl.adapter.api.taco.shop.mapper.input;

import cmahy.webapp.resource.api.taco.shop.vo.input.IngredientUpdateApiVo;
import cmahy.webapp.resource.impl.application.taco.shop.vo.input.IngredientUpdateInputAppVo;
import cmahy.webapp.resource.impl.exception.NullException;
import jakarta.inject.Named;

@Named
public class IngredientUpdateInputApiMapper {

    public IngredientUpdateInputAppVo map(IngredientUpdateApiVo input) {
        if (input == null) {
            throw new NullException(IngredientUpdateApiVo.class);
        }

        return new IngredientUpdateInputAppVo(
            input.name(),
            input.type()
        );
    }
}
