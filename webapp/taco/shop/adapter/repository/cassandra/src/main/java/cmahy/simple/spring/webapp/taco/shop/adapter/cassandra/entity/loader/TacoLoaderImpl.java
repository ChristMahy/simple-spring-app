package cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.loader;

import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.domain.CassandraIngredient;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.domain.CassandraTaco;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.repository.cassandra.CassandraIngredientRepository;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.id.IngredientId;
import jakarta.inject.Named;

import java.util.List;
import java.util.Set;

@Named
public class TacoLoaderImpl implements TacoLoader {

    private final CassandraIngredientRepository ingredientRepository;

    public TacoLoaderImpl(
        CassandraIngredientRepository ingredientRepository
    ) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public Class<CassandraTaco> entityClass() {
        return CassandraTaco.class;
    }

    @Override
    public List<CassandraIngredient> loadIngredients(Set<IngredientId> ingredientIds) {
        return ingredientRepository.findAllById(ingredientIds);
    }
}
