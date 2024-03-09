package cmahy.webapp.resource.impl.application.taco.shop.repository;

import cmahy.webapp.resource.impl.domain.taco.Ingredient;

import java.util.Optional;
import java.util.Set;

public interface IngredientRepository {

    Optional<Ingredient> findById(String id);

    Set<Ingredient> findByType(Ingredient.Type type);
}
