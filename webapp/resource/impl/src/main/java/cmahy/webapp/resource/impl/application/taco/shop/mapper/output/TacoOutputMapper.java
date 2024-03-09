package cmahy.webapp.resource.impl.application.taco.shop.mapper.output;

import cmahy.webapp.resource.impl.domain.taco.id.TacoId;
import cmahy.webapp.resource.impl.application.taco.shop.vo.output.TacoOutputAppVo;
import cmahy.webapp.resource.impl.domain.taco.Taco;
import cmahy.webapp.resource.impl.exception.NullException;
import jakarta.inject.Named;

import java.util.Objects;

@Named
public class TacoOutputMapper {

    private final IngredientOutputMapper ingredientOutputMapper;

    public TacoOutputMapper(IngredientOutputMapper ingredientOutputMapper) {
        this.ingredientOutputMapper = ingredientOutputMapper;
    }

    public TacoOutputAppVo map(Taco taco) {
        if (Objects.isNull(taco)) {
            throw new NullException(Taco.class);
        }

        return new TacoOutputAppVo(
            new TacoId(taco.getId()),
            taco.getCreatedAt(),
            taco.getName(),
            taco.getIngredients().stream().map(ingredientOutputMapper::map).toList()
        );
    }
}
