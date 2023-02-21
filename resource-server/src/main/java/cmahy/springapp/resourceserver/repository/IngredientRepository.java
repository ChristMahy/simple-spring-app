package cmahy.springapp.resourceserver.repository;

import cmahy.springapp.resourceserver.domain.Ingredient;

import java.util.Optional;

public interface IngredientRepository {
    Iterable<Ingredient> findAll();

    Optional<Ingredient> findById(String id);

    Ingredient save(Ingredient ingredient);

    void deleteById(String id);
}
