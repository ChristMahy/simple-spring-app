package cmahy.webapp.taco.shop.kernel.application.query;

import cmahy.common.annotation.Query;
import cmahy.webapp.taco.shop.kernel.application.mapper.output.IngredientOutputMapper;
import cmahy.webapp.taco.shop.kernel.application.repository.IngredientRepository;
import cmahy.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.webapp.taco.shop.kernel.domain.IngredientType;
import cmahy.webapp.taco.shop.kernel.exception.RequiredException;
import cmahy.webapp.taco.shop.kernel.vo.output.IngredientOutputVo;
import jakarta.inject.Named;

import java.util.*;

@Query
@Named
public class GetIngredientByTypeQuery {

    private final IngredientRepository<Ingredient> ingredientRepository;
    private final IngredientOutputMapper ingredientOutputMapper;

    public GetIngredientByTypeQuery(
        IngredientRepository ingredientRepository,
        IngredientOutputMapper ingredientOutputMapper
    ) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientOutputMapper = ingredientOutputMapper;
    }

    public Set<IngredientOutputVo> execute(IngredientType type) throws RequiredException {

        Set<Ingredient> ingredients = ingredientRepository.findByType(type);
        Set<IngredientOutputVo> ingredientsVo = new HashSet<>(ingredients.size());

        for (Ingredient ingredient : ingredients) {
            ingredientsVo.add(ingredientOutputMapper.map(ingredient));
        }

        return Collections.unmodifiableSet(ingredientsVo);
    }
}
