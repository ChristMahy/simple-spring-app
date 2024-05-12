package cmahy.webapp.resource.impl.adapter.ui.taco.shop.mapper.input;

import cmahy.webapp.resource.impl.domain.taco.id.IngredientId;
import cmahy.webapp.resource.impl.exception.NullException;
import cmahy.webapp.resource.ui.taco.vo.id.IngredientUiId;
import jakarta.inject.Named;

import java.util.Objects;

@Named
public class IngredientIdInputUiToAppMapper {

    public IngredientId map(IngredientUiId input) {
        if (Objects.isNull(input)) {
            throw new NullException(IngredientUiId.class);
        }

        return new IngredientId(
            input.value()
        );
    }
}
