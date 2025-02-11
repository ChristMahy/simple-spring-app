package cmahy.webapp.taco.shop.adapter.cassandra.entity.loader;

import cmahy.webapp.taco.shop.adapter.cassandra.entity.proxy.CassandraIngredientProxy;
import cmahy.webapp.taco.shop.adapter.cassandra.entity.proxy.factory.CassandraIngredientProxyFactory;
import cmahy.webapp.taco.shop.adapter.cassandra.repository.cassandra.CassandraIngredientRepository;
import cmahy.webapp.taco.shop.kernel.domain.id.IngredientId;
import jakarta.inject.Named;
import org.springframework.context.annotation.Lazy;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Named
public class TacoLoaderImpl implements TacoLoader {

    private final CassandraIngredientRepository ingredientRepository;
    private final CassandraIngredientProxyFactory ingredientProxyFactory;

    public TacoLoaderImpl(
        CassandraIngredientRepository ingredientRepository,
        @Lazy CassandraIngredientProxyFactory ingredientProxyFactory
    ) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientProxyFactory = ingredientProxyFactory;
    }

    @Override
    public List<CassandraIngredientProxy> loadIngredients(Set<IngredientId> ingredientIds) {
        return ingredientRepository.findAllById(ingredientIds).stream()
            .map(ingredientProxyFactory::create)
            .collect(Collectors.toList());
    }
}
