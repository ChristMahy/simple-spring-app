package cmahy.webapp.resource.impl.application.taco.shop.service;

import cmahy.webapp.resource.impl.application.taco.shop.vo.input.IngredientCreateInputAppVo;
import cmahy.webapp.resource.impl.domain.taco.Ingredient;
import cmahy.webapp.resource.impl.exception.NullException;
import jakarta.inject.Named;
import jakarta.validation.Valid;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

@Named
public class IngredientFactory {

    public @Valid Ingredient create(IngredientCreateInputAppVo ingredientCreateInputAppVo) {
        if (Objects.isNull(ingredientCreateInputAppVo)) {
            throw new NullException(IngredientCreateInputAppVo.class);
        }

        return new Ingredient()
            .setName(
                Optional.ofNullable(ingredientCreateInputAppVo.name())
                    .map(String::trim)
                    .orElse(null)
            )
            .setType(
                Optional.ofNullable(ingredientCreateInputAppVo.type())
                    .map(String::trim)
                    .filter(typeTrimmed -> Arrays.stream(Ingredient.Type.values()).anyMatch(t -> StringUtils.equals(typeTrimmed, t.name())))
                    .map(Ingredient.Type::valueOf)
                    .orElse(null)
            );
    }
}
