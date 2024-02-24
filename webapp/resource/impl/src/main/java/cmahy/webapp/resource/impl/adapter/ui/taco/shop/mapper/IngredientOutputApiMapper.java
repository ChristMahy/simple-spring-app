package cmahy.webapp.resource.impl.adapter.ui.taco.shop.mapper;

import cmahy.webapp.resource.impl.application.taco.shop.vo.output.IngredientOutputAppVo;
import cmahy.webapp.resource.ui.taco.vo.id.IngredientApiId;
import cmahy.webapp.resource.ui.taco.vo.output.IngredientOutputApiVo;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class IngredientOutputApiMapper {

    public IngredientOutputApiVo map(IngredientOutputAppVo output) {
        if (Objects.isNull(output)) {
            return null;
        }

        return new IngredientOutputApiVo(
            Objects.isNull(output.id()) ? null : new IngredientApiId(output.id().value()),
            output.name(),
            output.type()
        );
    }
}
