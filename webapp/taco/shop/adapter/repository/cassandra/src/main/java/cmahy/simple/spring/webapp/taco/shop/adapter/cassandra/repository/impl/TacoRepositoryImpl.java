package cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.repository.impl;

import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.domain.CassandraTaco;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.CassandraTacoProxy;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.factory.CassandraTacoProxyFactory;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.factory.provider.CassandraTacoProxyFactoryProvider;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.repository.cassandra.CassandraTacoRepository;
import cmahy.simple.spring.webapp.taco.shop.kernel.application.repository.TacoRepository;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.id.IngredientId;
import jakarta.inject.Named;
import org.springframework.context.annotation.Primary;

import java.util.*;
import java.util.stream.Collectors;

@Primary
@Named
public class TacoRepositoryImpl implements TacoRepository<CassandraTacoProxy> {

    private final CassandraTacoRepository tacoRepository;
    private final CassandraTacoProxyFactoryProvider factoryProvider;

    public TacoRepositoryImpl(
        CassandraTacoRepository tacoRepository,
        CassandraTacoProxyFactoryProvider factoryProvider
    ) {
        this.tacoRepository = tacoRepository;
        this.factoryProvider = factoryProvider;
    }

    @Override
    public CassandraTacoProxy save(CassandraTacoProxy taco) {
        CassandraTaco tacoUnwrapped = taco.unwrap();

        if (Objects.isNull(tacoUnwrapped.getId())) {
            tacoUnwrapped.setId(UUID.randomUUID());
        }

        if (Objects.isNull(tacoUnwrapped.getCreatedAt())) {
            tacoUnwrapped.setCreatedAt(new Date());
        }

        CassandraTacoProxyFactory factory = factoryProvider.resolve(CassandraTaco.class);

        return factory.create(
            tacoRepository.save(tacoUnwrapped)
        );
    }

    @Override
    public Set<CassandraTacoProxy> findByIngredientId(IngredientId ingredientId) {
        CassandraTacoProxyFactory factory = factoryProvider.resolve(CassandraTaco.class);

        return tacoRepository.findByIngredientId(ingredientId).stream()
            .map(factory::create)
            .collect(Collectors.toSet());
    }
}
