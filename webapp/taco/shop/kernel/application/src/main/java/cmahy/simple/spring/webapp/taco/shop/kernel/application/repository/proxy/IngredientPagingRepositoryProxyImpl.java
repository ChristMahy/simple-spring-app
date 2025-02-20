package cmahy.simple.spring.webapp.taco.shop.kernel.application.repository.proxy;


import cmahy.simple.spring.common.entity.page.EntityPageable;
import cmahy.simple.spring.webapp.taco.shop.kernel.application.repository.IngredientPagingRepository;
import cmahy.simple.spring.webapp.taco.shop.kernel.application.repository.annotation.RemoteRepository;
import cmahy.simple.spring.webapp.taco.shop.kernel.application.repository.proxy.annotation.RemoteOrDefault;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.page.IngredientPage;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Named
@RemoteOrDefault
public class IngredientPagingRepositoryProxyImpl implements IngredientPagingRepository<Ingredient> {

    private static final Logger LOG = LoggerFactory.getLogger(IngredientPagingRepositoryProxyImpl.class);

    private final IngredientPagingRepository<Ingredient> instance;

    public IngredientPagingRepositoryProxyImpl(
        IngredientPagingRepository defaultIngredientPagingRepository,
        @RemoteRepository Optional<IngredientPagingRepository> externalIngredientRepositoryImpl
    ) {
        this.instance = externalIngredientRepositoryImpl.orElse(defaultIngredientPagingRepository);
    }

    @Override
    public IngredientPage<Ingredient> findAll(EntityPageable pageable) {
        LOG.info("ingredientPagingRepositoryProxyImpl::instance.class <{}>", instance.getClass().getName());

        return instance.findAll(pageable);
    }
}
