package cmahy.simple.spring.webapp.taco.shop.kernel.application.repository;

import cmahy.simple.spring.common.entity.page.EntityPageable;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.page.IngredientPage;

public interface IngredientPagingRepository<I extends Ingredient> {

    IngredientPage<I> findAll(EntityPageable pageable);
}
