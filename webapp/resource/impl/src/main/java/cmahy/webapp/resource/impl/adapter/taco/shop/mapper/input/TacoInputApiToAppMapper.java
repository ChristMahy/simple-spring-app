package cmahy.webapp.resource.impl.adapter.taco.shop.mapper.input;

import cmahy.webapp.resource.impl.application.taco.shop.vo.input.TacoInputAppVo;
import cmahy.webapp.resource.impl.exception.NullException;
import cmahy.webapp.resource.ui.taco.vo.input.TacoInputApiVo;
import jakarta.inject.Named;

import java.util.Objects;
import java.util.stream.Collectors;

@Named
public class TacoInputApiToAppMapper {

    private final IngredientIdInputApiToAppMapper ingredientIdMapper;

    public TacoInputApiToAppMapper(IngredientIdInputApiToAppMapper ingredientIdMapper) {
        this.ingredientIdMapper = ingredientIdMapper;
    }

    public TacoInputAppVo map(TacoInputApiVo input) {
        if (Objects.isNull(input)) {
            throw new NullException(TacoInputApiVo.class);
        }

        return new TacoInputAppVo(
            input.name(),
            input.ingredientIds().stream().map(ingredientIdMapper::map).collect(Collectors.toSet())
        );
    }
}
