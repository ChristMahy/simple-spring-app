package cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.loader;

import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.CassandraIngredientProxy;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.id.IngredientId;

import java.util.List;
import java.util.Set;

public interface TacoLoader {

    List<CassandraIngredientProxy> loadIngredients(Set<IngredientId> ingredientIds);
}
