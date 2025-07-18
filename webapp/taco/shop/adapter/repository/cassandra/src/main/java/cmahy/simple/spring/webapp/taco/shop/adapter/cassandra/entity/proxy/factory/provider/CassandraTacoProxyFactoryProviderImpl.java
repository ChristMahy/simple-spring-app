package cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.factory.provider;

import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.domain.*;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.loader.provider.TacoLoaderProvider;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.factory.*;
import jakarta.inject.Named;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

import java.util.Map;
import java.util.Optional;

@Named
@ConditionalOnMissingBean(CassandraTacoProxyFactoryProvider.class)
public class CassandraTacoProxyFactoryProviderImpl implements CassandraTacoProxyFactoryProvider {

    private final Map<Class<?>, CassandraProxyFactory> factories;

    public CassandraTacoProxyFactoryProviderImpl(
        TacoLoaderProvider tacoLoaderProvider
    ) {
        this.factories = Map.of(
            CassandraClientOrder.class, new CassandraClientOrderProxyFactory(tacoLoaderProvider, this),
            CassandraIngredient.class, new CassandraIngredientProxyFactory(),
            CassandraTaco.class, new CassandraTacoProxyFactory(tacoLoaderProvider, this)
        );
    }

    @Override
    public <T, F> F resolve(Class<T> entityClass) {
        return Optional.ofNullable(entityClass)
            .filter(factories::containsKey)
            .map(ec -> (F) factories.get(ec))
            .orElseThrow(() -> new IllegalArgumentException("Enity class not match"));
    }

}
