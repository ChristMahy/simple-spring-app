package cmahy.springapp.repository;

import cmahy.springapp.domain.Ingredient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JdbcIngredientRepository extends IngredientRepository, CrudRepository<Ingredient, String> {
}
