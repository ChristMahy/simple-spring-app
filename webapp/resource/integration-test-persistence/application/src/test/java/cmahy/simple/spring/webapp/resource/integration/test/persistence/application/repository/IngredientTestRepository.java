package cmahy.simple.spring.webapp.resource.integration.test.persistence.application.repository;

import cmahy.simple.spring.webapp.taco.shop.kernel.domain.Ingredient;

public interface IngredientTestRepository<I extends Ingredient> {

    I save(I ingredient);

}
