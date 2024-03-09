package cmahy.webapp.resource.impl.adapter.ui.taco.shop.mapper;

import cmahy.webapp.resource.impl.application.taco.shop.vo.output.IngredientOutputAppVo;
import cmahy.webapp.resource.impl.exception.NullException;
import cmahy.webapp.resource.ui.taco.vo.id.IngredientApiId;
import cmahy.webapp.resource.ui.taco.vo.output.IngredientOutputApiVo;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class IngredientOutputApiMapper {

    public IngredientOutputApiVo map(IngredientOutputAppVo output) {
        if (Objects.isNull(output)) {
            throw new NullException(IngredientOutputAppVo.class);
        }

        return new IngredientOutputApiVo(
            Objects.isNull(output.id()) ? null : new IngredientApiId(output.id().value()),
            output.name(),
            output.type()
        );
    }
}
