package cmahy.webapp.taco.shop.kernel.application.repository;

import cmahy.common.entity.page.EntityPageable;
import cmahy.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.webapp.taco.shop.kernel.domain.page.IngredientPage;

public interface IngredientPagingRepository<I extends Ingredient> {

    IngredientPage<I> findAll(EntityPageable pageable);
}
