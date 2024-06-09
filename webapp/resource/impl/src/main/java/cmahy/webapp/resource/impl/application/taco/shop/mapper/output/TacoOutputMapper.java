package cmahy.webapp.resource.impl.application.taco.shop.mapper.output;

import cmahy.webapp.resource.impl.domain.taco.Taco;
import cmahy.webapp.resource.impl.exception.NullException;
import cmahy.webapp.resource.taco.shop.id.TacoId;
import cmahy.webapp.resource.taco.shop.vo.output.TacoOutputVo;
import jakarta.inject.Named;

import java.util.Objects;

@Named
public class TacoOutputMapper {

    private final IngredientOutputMapper ingredientOutputMapper;

    public TacoOutputMapper(IngredientOutputMapper ingredientOutputMapper) {
        this.ingredientOutputMapper = ingredientOutputMapper;
    }

    public TacoOutputVo map(Taco taco) {
        if (Objects.isNull(taco)) {
            throw new NullException(Taco.class);
        }

        return new TacoOutputVo(
            new TacoId(taco.getId()),
            taco.getCreatedAt(),
            taco.getName(),
            taco.getIngredients().stream().map(ingredientOutputMapper::map).toList()
        );
    }
}
