package cmahy.simple.spring.webapp.taco.shop.kernel.application.repository;

import cmahy.simple.spring.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.IngredientType;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.id.IngredientId;

import java.util.Optional;
import java.util.Set;

public interface IngredientRepository<I extends Ingredient> {

    Optional<I> findById(IngredientId id);

    Set<I> findByType(IngredientType type);

    Optional<I> findByNameAndType(String name, IngredientType type);

    I save(I ingredient);

    void deleteById(IngredientId id);
}
