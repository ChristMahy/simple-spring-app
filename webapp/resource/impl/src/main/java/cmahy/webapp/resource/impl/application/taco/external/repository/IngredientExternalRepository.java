package cmahy.webapp.resource.impl.application.taco.external.repository;

import cmahy.webapp.resource.impl.domain.taco.external.page.IngredientExternalPage;

public interface IngredientExternalRepository {

    IngredientExternalPage findAllIngredients();
}
