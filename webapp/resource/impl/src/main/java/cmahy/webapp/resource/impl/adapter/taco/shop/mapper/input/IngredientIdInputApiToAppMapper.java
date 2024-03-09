package cmahy.webapp.resource.impl.adapter.taco.shop.mapper.input;

import cmahy.webapp.resource.impl.domain.taco.id.IngredientId;
import cmahy.webapp.resource.impl.exception.NullException;
import cmahy.webapp.resource.ui.taco.vo.id.IngredientApiId;
import jakarta.inject.Named;

import java.util.Objects;

@Named
public class IngredientIdInputApiToAppMapper {

    public IngredientId map(IngredientApiId input) {
        if (Objects.isNull(input)) {
            throw new NullException(IngredientApiId.class);
        }

        return new IngredientId(
            input.value()
        );
    }
}
