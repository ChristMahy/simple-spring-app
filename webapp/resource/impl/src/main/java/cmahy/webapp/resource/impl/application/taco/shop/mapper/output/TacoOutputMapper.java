package cmahy.webapp.resource.impl.application.taco.shop.mapper.output;

import cmahy.webapp.resource.impl.domain.taco.id.TacoId;
import cmahy.webapp.resource.impl.application.taco.shop.vo.output.TacoOutputAppVo;
import cmahy.webapp.resource.impl.domain.taco.Taco;
import jakarta.inject.Named;

@Named
public class TacoOutputMapper {

    private final IngredientOutputMapper ingredientOutputMapper;

    public TacoOutputMapper(IngredientOutputMapper ingredientOutputMapper) {
        this.ingredientOutputMapper = ingredientOutputMapper;
    }

    public TacoOutputAppVo map(Taco taco) {
        return new TacoOutputAppVo(
            new TacoId(taco.getId()),
            taco.getCreatedAt(),
            taco.getName(),
            taco.getIngredients().stream().map(ingredientOutputMapper::map).toList()
        );
    }
}
