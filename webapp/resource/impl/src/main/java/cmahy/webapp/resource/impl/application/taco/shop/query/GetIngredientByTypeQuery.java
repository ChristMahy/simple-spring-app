package cmahy.webapp.resource.impl.application.taco.shop.query;

import cmahy.common.annotation.Query;
import cmahy.webapp.resource.impl.application.taco.shop.mapper.output.IngredientOutputMapper;
import cmahy.webapp.resource.impl.application.taco.shop.repository.IngredientRepository;
import cmahy.webapp.resource.impl.domain.taco.Ingredient;
import cmahy.webapp.resource.taco.shop.vo.output.IngredientOutputVo;
import jakarta.inject.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Query
@Named
public class GetIngredientByTypeQuery {

    private final IngredientRepository ingredientRepository;
    private final IngredientOutputMapper ingredientOutputMapper;

    public GetIngredientByTypeQuery(
        IngredientRepository ingredientRepository,
        IngredientOutputMapper ingredientOutputMapper
    ) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientOutputMapper = ingredientOutputMapper;
    }

    public Set<IngredientOutputVo> execute(Ingredient.Type type) {
        return ingredientRepository
            .findByType(type)
            .stream()
            .map(ingredientOutputMapper::map)
            .collect(Collectors.toSet());
    }
}
