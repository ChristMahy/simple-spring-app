package cmahy.simple.spring.webapp.resource.integration.test.persistence.cassandra.repository.impl;

import cmahy.simple.spring.webapp.resource.integration.test.persistence.api.repository.IngredientTestRepository;
import cmahy.simple.spring.webapp.resource.integration.test.persistence.cassandra.repository.cassandra.CassandraIngredientTestRepository;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.domain.CassandraIngredient;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.CassandraIngredientProxy;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.factory.CassandraIngredientProxyFactory;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.factory.provider.CassandraTacoProxyFactoryProvider;
import jakarta.inject.Named;

import java.util.Objects;
import java.util.UUID;

@Named
public class IngredientTestRepositoryImpl implements IngredientTestRepository<CassandraIngredientProxy> {

    private final CassandraIngredientTestRepository cassandraIngredientTestRepository;
    private final CassandraTacoProxyFactoryProvider factoryProvider;

    public IngredientTestRepositoryImpl(
        CassandraIngredientTestRepository cassandraIngredientTestRepository,
        CassandraTacoProxyFactoryProvider factoryProvider
    ) {
        this.cassandraIngredientTestRepository = cassandraIngredientTestRepository;
        this.factoryProvider = factoryProvider;
    }

    @Override
    public CassandraIngredientProxy save(CassandraIngredientProxy ingredient) {

        CassandraIngredient ingredientUnwrapped = ingredient.unwrap();

        if (Objects.isNull(ingredientUnwrapped.getId())) {
            ingredientUnwrapped.setId(UUID.randomUUID());
        }

        CassandraIngredientProxyFactory factory = factoryProvider.resolve(CassandraIngredient.class);

        return factory.create(
            cassandraIngredientTestRepository.save(ingredientUnwrapped)
        );

    }

}
