package cmahy.webapp.taco.shop.adapter.cassandra.entity.loader;

import cmahy.webapp.taco.shop.adapter.cassandra.entity.proxy.CassandraIngredientProxy;
import cmahy.webapp.taco.shop.kernel.domain.id.IngredientId;

import java.util.List;
import java.util.Set;

public interface TacoLoader {

    List<CassandraIngredientProxy> loadIngredients(Set<IngredientId> ingredientIds);
}
