package cmahy.webapp.resource.impl.application.taco.external.query;

import cmahy.common.annotation.Query;
import cmahy.webapp.resource.impl.application.taco.external.repository.IngredientExternalRepository;
import cmahy.webapp.resource.impl.domain.taco.external.page.IngredientExternalPage;
import jakarta.inject.Named;

@Query
@Named
public class GetAllExternalIngredientQuery {

    private final IngredientExternalRepository ingredientExternalRepository;

    public GetAllExternalIngredientQuery(
        IngredientExternalRepository ingredientExternalRepository
    ) {
        this.ingredientExternalRepository = ingredientExternalRepository;
    }

    public IngredientExternalPage execute() {
        return ingredientExternalRepository.findAllIngredients();
    }
}
