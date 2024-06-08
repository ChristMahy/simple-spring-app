package cmahy.webapp.resource.impl.adapter.api.taco.shop.mapper.input;

import cmahy.webapp.resource.api.taco.shop.vo.input.IngredientCreateApiVo;
import cmahy.webapp.resource.impl.application.taco.shop.vo.input.IngredientCreateInputAppVo;
import cmahy.webapp.resource.impl.exception.NullException;
import jakarta.inject.Named;

@Named
public class IngredientCreateInputApiMapper {

    public IngredientCreateInputAppVo map(IngredientCreateApiVo input) {

        if (input == null) {
            throw new NullException(IngredientCreateApiVo.class);
        }

        return new IngredientCreateInputAppVo(
            input.name(),
            input.type()
        );
    }
}
