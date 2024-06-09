package cmahy.webapp.resource.impl.application.taco.shop.service;

import cmahy.webapp.resource.impl.application.taco.shop.mapper.output.IngredientOutputMapper;
import cmahy.webapp.resource.impl.application.taco.shop.repository.IngredientRepository;
import cmahy.webapp.resource.impl.domain.taco.Ingredient;
import cmahy.webapp.resource.impl.exception.taco.IngredientNotFoundException;
import cmahy.webapp.resource.taco.shop.id.IngredientId;
import cmahy.webapp.resource.taco.shop.vo.input.IngredientUpdateInputVo;
import cmahy.webapp.resource.taco.shop.vo.output.IngredientOutputVo;
import jakarta.inject.Named;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

@Named
public class PartialUpdatingAnIngredient {

    private final IngredientRepository ingredientRepository;
    private final IngredientOutputMapper ingredientOutputMapper;

    public PartialUpdatingAnIngredient(
        IngredientRepository ingredientRepository,
        IngredientOutputMapper ingredientOutputMapper
    ) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientOutputMapper = ingredientOutputMapper;
    }

    public IngredientOutputVo execute(IngredientId id, IngredientUpdateInputVo inputVo) {
        Ingredient ingredient = ingredientRepository.findById(id.value())
            .orElseThrow(() -> new IngredientNotFoundException(id));

        ingredient = ingredient
            .setName(
                inputVo.name()
                    .map(String::trim)
                    .filter(StringUtils::isNotBlank)
                    .orElse(ingredient.getName())
            )
            .setType(
                inputVo.type()
                    .map(String::trim)
                    .filter(typeTrimmed -> Arrays.stream(Ingredient.Type.values()).anyMatch(t -> StringUtils.equals(typeTrimmed, t.name())))
                    .map(Ingredient.Type::valueOf)
                    .orElse(ingredient.getType())
            );

        return ingredientOutputMapper.map(
            ingredientRepository.save(ingredient)
        );
    }
}
