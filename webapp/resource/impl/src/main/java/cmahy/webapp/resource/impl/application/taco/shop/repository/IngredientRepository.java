package cmahy.webapp.resource.impl.application.taco.shop.repository;

import cmahy.webapp.resource.impl.domain.taco.Ingredient;
import cmahy.webapp.resource.impl.domain.taco.IngredientType;

import java.util.Optional;
import java.util.Set;

public interface IngredientRepository {

    Optional<Ingredient> findById(String id);

    Set<Ingredient> findByType(IngredientType type);

    Optional<Ingredient> findByNameAndType(String name, IngredientType type);

    Ingredient save(Ingredient ingredient);

    void deleteById(String id);
}
