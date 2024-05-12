package cmahy.webapp.resource.impl.adapter.ui.taco.shop.mapper.output;

import cmahy.webapp.resource.impl.application.taco.shop.vo.output.TacoOutputAppVo;
import cmahy.webapp.resource.impl.exception.NullException;
import cmahy.webapp.resource.ui.taco.vo.id.TacoUiId;
import cmahy.webapp.resource.ui.taco.vo.output.TacoOutputUiVo;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TacoOutputUiMapper {

    private final IngredientOutputUiMapper ingredientOutputUiMapper;

    public TacoOutputUiMapper(IngredientOutputUiMapper ingredientOutputUiMapper) {
        this.ingredientOutputUiMapper = ingredientOutputUiMapper;
    }

    public TacoOutputUiVo map(TacoOutputAppVo source) {
        if (Objects.isNull(source)) {
            throw new NullException(TacoOutputAppVo.class);
        }

        return new TacoOutputUiVo(
            Objects.nonNull(source.id()) && Objects.nonNull(source.id().value()) ? new TacoUiId(source.id().value()) : null,
            source.createdAt(),
            source.name(),
            source.ingredients().stream().map(ingredientOutputUiMapper::map).toList()
        );
    }
}
