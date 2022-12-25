package cmahy.springapp.repository;

import cmahy.springapp.domain.Ingredient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaIngredientRepository extends IngredientRepository, CrudRepository<Ingredient, String> {
}
