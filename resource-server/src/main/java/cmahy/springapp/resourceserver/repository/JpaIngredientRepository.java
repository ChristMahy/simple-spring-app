package cmahy.springapp.resourceserver.repository;

import cmahy.springapp.resourceserver.domain.Ingredient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaIngredientRepository extends IngredientRepository, CrudRepository<Ingredient, String> {
}
