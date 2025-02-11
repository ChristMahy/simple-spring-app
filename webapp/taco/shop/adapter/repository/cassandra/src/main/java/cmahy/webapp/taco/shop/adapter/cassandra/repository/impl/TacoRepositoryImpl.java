package cmahy.webapp.taco.shop.adapter.cassandra.repository.impl;

import cmahy.webapp.taco.shop.adapter.cassandra.entity.domain.CassandraTaco;
import cmahy.webapp.taco.shop.adapter.cassandra.entity.proxy.CassandraTacoProxy;
import cmahy.webapp.taco.shop.adapter.cassandra.entity.proxy.factory.CassandraTacoProxyFactory;
import cmahy.webapp.taco.shop.adapter.cassandra.repository.cassandra.CassandraTacoRepository;
import cmahy.webapp.taco.shop.kernel.application.repository.TacoRepository;
import cmahy.webapp.taco.shop.kernel.domain.id.IngredientId;
import jakarta.inject.Named;
import org.springframework.context.annotation.Primary;

import java.util.*;
import java.util.stream.Collectors;

@Primary
@Named
public class TacoRepositoryImpl implements TacoRepository<CassandraTacoProxy> {

    private final CassandraTacoRepository tacoRepository;
    private final CassandraTacoProxyFactory tacoProxyFactory;

    public TacoRepositoryImpl(
        CassandraTacoRepository tacoRepository,
        CassandraTacoProxyFactory tacoProxyFactory
    ) {
        this.tacoRepository = tacoRepository;
        this.tacoProxyFactory = tacoProxyFactory;
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

        return tacoProxyFactory.create(
            tacoRepository.save(tacoUnwrapped)
        );
    }

    @Override
    public Set<CassandraTacoProxy> findByIngredientId(IngredientId ingredientId) {
        return tacoRepository.findByIngredientId(ingredientId).stream()
            .map(tacoProxyFactory::create)
            .collect(Collectors.toSet());
    }
}
