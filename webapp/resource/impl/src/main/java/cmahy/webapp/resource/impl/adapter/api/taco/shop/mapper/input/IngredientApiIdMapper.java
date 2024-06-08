package cmahy.webapp.resource.impl.adapter.api.taco.shop.mapper.input;

import cmahy.webapp.resource.api.taco.shop.vo.id.IngredientApiId;
import cmahy.webapp.resource.impl.domain.taco.id.IngredientId;
import cmahy.webapp.resource.impl.exception.NullException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class IngredientApiIdMapper {

    public IngredientId map(IngredientApiId id) {

        if (Objects.isNull(id)) {
            throw new NullException(IngredientApiId.class);
        }

        return new IngredientId(id.value());
    }
}
