package cmahy.webapp.taco.shop.kernel.application.repository;

import cmahy.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.webapp.taco.shop.kernel.domain.IngredientType;
import cmahy.webapp.taco.shop.kernel.domain.id.IngredientId;

import java.util.Optional;
import java.util.Set;

public interface IngredientRepository {

    Optional<Ingredient> findById(IngredientId id);

    Set<Ingredient> findByType(IngredientType type);

    Optional<Ingredient> findByNameAndType(String name, IngredientType type);

    Ingredient save(Ingredient ingredient);

    void deleteById(IngredientId id);
}
