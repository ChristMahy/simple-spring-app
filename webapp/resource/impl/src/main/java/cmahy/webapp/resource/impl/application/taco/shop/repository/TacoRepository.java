package cmahy.webapp.resource.impl.application.taco.shop.repository;

import cmahy.webapp.resource.impl.domain.taco.Taco;
import cmahy.webapp.resource.taco.shop.id.IngredientId;

import java.util.Set;

public interface TacoRepository {
    Taco save(Taco taco);

    Set<Taco> findByIngredientId(IngredientId ingredientId);
}
