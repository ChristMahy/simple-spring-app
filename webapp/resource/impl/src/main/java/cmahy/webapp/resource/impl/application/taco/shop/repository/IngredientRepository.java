package cmahy.webapp.resource.impl.application.taco.shop.repository;

import cmahy.webapp.resource.impl.domain.taco.Ingredient;

import java.util.Set;

public interface IngredientRepository {

    Set<Ingredient> findByType(Ingredient.Type type);
}
