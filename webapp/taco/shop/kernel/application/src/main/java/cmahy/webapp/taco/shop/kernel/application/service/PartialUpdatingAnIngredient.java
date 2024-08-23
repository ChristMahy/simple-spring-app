package cmahy.webapp.taco.shop.kernel.application.service;

import cmahy.webapp.taco.shop.kernel.application.mapper.output.IngredientOutputMapper;
import cmahy.webapp.taco.shop.kernel.application.repository.IngredientRepository;
import cmahy.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.webapp.taco.shop.kernel.domain.IngredientType;
import cmahy.webapp.taco.shop.kernel.domain.id.IngredientId;
import cmahy.webapp.taco.shop.kernel.exception.RequiredException;
import cmahy.webapp.taco.shop.kernel.exception.ingredient.IngredientNotFoundException;
import cmahy.webapp.taco.shop.kernel.vo.input.IngredientUpdateInputVo;
import cmahy.webapp.taco.shop.kernel.vo.output.IngredientOutputVo;
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

    public IngredientOutputVo execute(IngredientId id, IngredientUpdateInputVo inputVo) throws IngredientNotFoundException, RequiredException {
        Ingredient ingredient = ingredientRepository.findById(id)
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
                    .filter(typeTrimmed -> Arrays.stream(IngredientType.values()).anyMatch(t -> StringUtils.equals(typeTrimmed, t.name())))
                    .map(IngredientType::valueOf)
                    .orElse(ingredient.getType())
            );

        return ingredientOutputMapper.map(
            ingredientRepository.save(ingredient)
        );
    }
}
