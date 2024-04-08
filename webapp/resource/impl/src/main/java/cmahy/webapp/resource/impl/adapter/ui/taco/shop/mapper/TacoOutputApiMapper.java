package cmahy.webapp.resource.impl.adapter.ui.taco.shop.mapper;

import cmahy.webapp.resource.impl.application.taco.shop.vo.output.TacoOutputAppVo;
import cmahy.webapp.resource.impl.exception.NullException;
import cmahy.webapp.resource.ui.taco.vo.id.TacoApiId;
import cmahy.webapp.resource.ui.taco.vo.output.TacoOutputApiVo;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TacoOutputApiMapper {

    private final IngredientOutputApiMapper ingredientOutputApiMapper;

    public TacoOutputApiMapper(IngredientOutputApiMapper ingredientOutputApiMapper) {
        this.ingredientOutputApiMapper = ingredientOutputApiMapper;
    }

    public TacoOutputApiVo map(TacoOutputAppVo source) {
        if (Objects.isNull(source)) {
            throw new NullException(TacoOutputAppVo.class);
        }

        return new TacoOutputApiVo(
            Objects.nonNull(source.id()) && Objects.nonNull(source.id().value()) ? new TacoApiId(source.id().value()) : null,
            source.createdAt(),
            source.name(),
            source.ingredients().stream().map(ingredientOutputApiMapper::map).toList()
        );
    }
}
