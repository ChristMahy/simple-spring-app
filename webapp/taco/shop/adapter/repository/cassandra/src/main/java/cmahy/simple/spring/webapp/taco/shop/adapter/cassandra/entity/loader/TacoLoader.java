package cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.loader;

import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.domain.CassandraIngredient;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.id.IngredientId;

import java.util.List;
import java.util.Set;

public interface TacoLoader extends Loader {

    List<CassandraIngredient> loadIngredients(Set<IngredientId> ingredientIds);
}
