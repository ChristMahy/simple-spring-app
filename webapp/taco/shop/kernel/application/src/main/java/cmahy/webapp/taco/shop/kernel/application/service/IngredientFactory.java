package cmahy.webapp.taco.shop.kernel.application.service;

import cmahy.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.webapp.taco.shop.kernel.domain.IngredientType;
import cmahy.webapp.taco.shop.kernel.exception.RequiredException;
import cmahy.webapp.taco.shop.kernel.vo.input.IngredientCreateInputVo;
import jakarta.inject.Named;
import jakarta.validation.Valid;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

@Named
public class IngredientFactory {

    public @Valid Ingredient create(IngredientCreateInputVo ingredientCreateInputVo) throws RequiredException {
        if (Objects.isNull(ingredientCreateInputVo)) {
            throw new RequiredException(IngredientCreateInputVo.class);
        }

        return new Ingredient()
            .setName(
                Optional.ofNullable(ingredientCreateInputVo.name())
                    .map(String::trim)
                    .orElse(null)
            )
            .setType(
                Optional.ofNullable(ingredientCreateInputVo.type())
                    .map(String::trim)
                    .filter(typeTrimmed -> Arrays.stream(IngredientType.values()).anyMatch(t -> StringUtils.equals(typeTrimmed, t.name())))
                    .map(IngredientType::valueOf)
                    .orElse(null)
            );
    }
}
