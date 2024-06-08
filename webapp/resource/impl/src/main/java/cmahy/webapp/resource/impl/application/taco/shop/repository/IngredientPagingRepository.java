package cmahy.webapp.resource.impl.application.taco.shop.repository;

import cmahy.common.entity.page.EntityPageable;
import cmahy.webapp.resource.impl.domain.taco.page.IngredientPage;

public interface IngredientPagingRepository {

    IngredientPage findAll(EntityPageable pageable);
}
