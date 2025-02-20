package cmahy.simple.spring.webapp.taco.shop.kernel.application.service;

import cmahy.simple.spring.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.IngredientType;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.builder.factory.IngredientBuilderFactory;
import cmahy.simple.spring.webapp.taco.shop.kernel.exception.RequiredException;
import cmahy.simple.spring.webapp.taco.shop.kernel.vo.input.IngredientCreateInputVo;
import jakarta.inject.Named;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

@Named
public class IngredientFactory {

    private final IngredientBuilderFactory<Ingredient> ingredientBuilderFactory;

    public IngredientFactory(IngredientBuilderFactory ingredientBuilderFactory) {
        this.ingredientBuilderFactory = ingredientBuilderFactory;
    }

    public @Valid @NotNull(message = Ingredient.I18N_KEY_NOT_NULL) Ingredient create(IngredientCreateInputVo ingredientCreateInputVo) throws RequiredException {
        if (Objects.isNull(ingredientCreateInputVo)) {
            throw new RequiredException(IngredientCreateInputVo.class);
        }

        return ingredientBuilderFactory.create()
            .name(
                Optional.ofNullable(ingredientCreateInputVo.name())
                    .map(String::trim)
                    .orElse(null)
            )
            .type(
                Optional.ofNullable(ingredientCreateInputVo.type())
                    .map(String::trim)
                    .filter(typeTrimmed -> Arrays.stream(IngredientType.values()).anyMatch(t -> StringUtils.equals(typeTrimmed, t.name())))
                    .map(IngredientType::valueOf)
                    .orElse(null)
            )
            .build();
    }
}
