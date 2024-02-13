package cmahy.webapp.resource.impl.adapter.taco.shop.repository;

import cmahy.webapp.resource.impl.application.taco.shop.repository.IngredientRepository;
import cmahy.webapp.resource.impl.domain.taco.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepositoryImpl extends IngredientRepository, JpaRepository<Ingredient, String> {
}
