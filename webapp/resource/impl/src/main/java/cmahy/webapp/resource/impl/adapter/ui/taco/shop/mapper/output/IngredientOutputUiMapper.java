package cmahy.webapp.resource.impl.adapter.ui.taco.shop.mapper.output;

import cmahy.webapp.resource.impl.application.taco.shop.vo.output.IngredientOutputAppVo;
import cmahy.webapp.resource.impl.exception.NullException;
import cmahy.webapp.resource.ui.taco.vo.id.IngredientUiId;
import cmahy.webapp.resource.ui.taco.vo.output.IngredientOutputUiVo;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class IngredientOutputUiMapper {

    public IngredientOutputUiVo map(IngredientOutputAppVo output) {
        if (Objects.isNull(output)) {
            throw new NullException(IngredientOutputAppVo.class);
        }

        return new IngredientOutputUiVo(
            Objects.isNull(output.id()) ? null : new IngredientUiId(output.id().value()),
            output.name(),
            output.type()
        );
    }
}
