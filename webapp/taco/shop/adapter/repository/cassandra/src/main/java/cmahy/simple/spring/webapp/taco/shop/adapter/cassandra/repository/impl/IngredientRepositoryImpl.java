package cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.repository.impl;

import cmahy.simple.spring.common.entity.page.EntityPageable;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.domain.CassandraIngredient;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.CassandraIngredientProxy;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy.factory.CassandraIngredientProxyFactory;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.repository.cassandra.CassandraIngredientRepository;
import cmahy.simple.spring.webapp.taco.shop.kernel.application.repository.IngredientPagingRepository;
import cmahy.simple.spring.webapp.taco.shop.kernel.application.repository.IngredientRepository;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.IngredientType;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.id.IngredientId;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.page.IngredientPage;
import jakarta.inject.Named;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Slice;

import java.util.*;
import java.util.stream.Collectors;

@Primary
@Named
public class IngredientRepositoryImpl implements
    IngredientRepository<CassandraIngredientProxy>,
    IngredientPagingRepository<CassandraIngredientProxy> {

    private final CassandraIngredientRepository ingredientRepository;
    private final CassandraIngredientProxyFactory ingredientProxyFactory;

    public IngredientRepositoryImpl(
        CassandraIngredientRepository ingredientRepository,
        CassandraIngredientProxyFactory ingredientProxyFactory
    ) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientProxyFactory = ingredientProxyFactory;
    }

    @Override
    public IngredientPage<CassandraIngredientProxy> findAll(EntityPageable pageable) {
        Slice<CassandraIngredient> all = ingredientRepository.findAll(pageable);

        return new IngredientPage<>(
            all.getContent().stream()
                .map(ingredientProxyFactory::create)
                .toList(),
            ingredientRepository.count()
        );
    }

    @Override
    public Optional<CassandraIngredientProxy> findById(IngredientId id) {
        return ingredientRepository.findById(id)
            .map(ingredientProxyFactory::create);
    }

    @Override
    public Set<CassandraIngredientProxy> findByType(IngredientType type) {
        return ingredientRepository.findByType(type).stream()
            .map(ingredientProxyFactory::create)
            .collect(Collectors.toSet());
    }

    @Override
    public Optional<CassandraIngredientProxy> findByNameAndType(String name, IngredientType type) {
        return ingredientRepository.findByNameAndType(name, type)
            .map(ingredientProxyFactory::create);
    }

    @Override
    public CassandraIngredientProxy save(CassandraIngredientProxy ingredient) {
        CassandraIngredient ingredientUnwrapped = ingredient.unwrap();

        if (Objects.isNull(ingredientUnwrapped.getId())) {
            ingredientUnwrapped.setId(UUID.randomUUID());
        }

        return ingredientProxyFactory.create(
            ingredientRepository.save(ingredientUnwrapped)
        );
    }

    @Override
    public void deleteById(IngredientId id) {
        ingredientRepository.deleteById(id);
    }
}
