package cmahy.webapp.resource.impl.domain.taco.page;

import cmahy.common.entity.page.EntityPage;
import cmahy.webapp.resource.impl.domain.taco.Ingredient;

import java.util.Collection;

public record IngredientPage(
    Collection<Ingredient> content,
    Long totalElements
) implements EntityPage<Ingredient> {
}
