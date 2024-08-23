package cmahy.webapp.taco.shop.kernel.application.repository;

import cmahy.common.entity.page.EntityPageable;
import cmahy.webapp.taco.shop.kernel.domain.page.IngredientPage;

public interface IngredientPagingRepository {

    IngredientPage findAll(EntityPageable pageable);
}
