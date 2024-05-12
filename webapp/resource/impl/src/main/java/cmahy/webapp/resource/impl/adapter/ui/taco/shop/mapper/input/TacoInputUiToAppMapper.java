package cmahy.webapp.resource.impl.adapter.ui.taco.shop.mapper.input;

import cmahy.webapp.resource.impl.application.taco.shop.vo.input.TacoInputAppVo;
import cmahy.webapp.resource.impl.exception.NullException;
import cmahy.webapp.resource.ui.taco.vo.input.TacoInputUiVo;
import jakarta.inject.Named;

import java.util.Objects;
import java.util.stream.Collectors;

@Named
public class TacoInputUiToAppMapper {

    private final IngredientIdInputUiToAppMapper ingredientIdMapper;

    public TacoInputUiToAppMapper(IngredientIdInputUiToAppMapper ingredientIdMapper) {
        this.ingredientIdMapper = ingredientIdMapper;
    }

    public TacoInputAppVo map(TacoInputUiVo input) {
        if (Objects.isNull(input)) {
            throw new NullException(TacoInputUiVo.class);
        }

        return new TacoInputAppVo(
            input.name(),
            input.ingredientIds().stream().map(ingredientIdMapper::map).collect(Collectors.toSet())
        );
    }
}
