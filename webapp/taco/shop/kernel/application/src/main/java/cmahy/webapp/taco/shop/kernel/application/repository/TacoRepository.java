package cmahy.webapp.taco.shop.kernel.application.repository;

import cmahy.webapp.taco.shop.kernel.domain.Taco;
import cmahy.webapp.taco.shop.kernel.domain.id.IngredientId;

import java.util.Set;

public interface TacoRepository<T extends Taco> {
    T save(T taco);

    Set<T> findByIngredientId(IngredientId ingredientId);
}
