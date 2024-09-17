package cmahy.webapp.taco.shop.kernel.application.repository.proxy;


import cmahy.common.entity.page.EntityPageable;
import cmahy.webapp.taco.shop.kernel.application.repository.IngredientPagingRepository;
import cmahy.webapp.taco.shop.kernel.application.repository.annotation.RemoteRepository;
import cmahy.webapp.taco.shop.kernel.application.repository.proxy.annotation.RemoteOrDefault;
import cmahy.webapp.taco.shop.kernel.domain.page.IngredientPage;
import jakarta.inject.Named;

import java.util.Optional;

@Named
@RemoteOrDefault
public class IngredientPagingRepositoryProxyImpl implements IngredientPagingRepository {

    private final IngredientPagingRepository instance;

    public IngredientPagingRepositoryProxyImpl(
        IngredientPagingRepository defaultIngredientPagingRepository,
        @RemoteRepository Optional<IngredientPagingRepository> remoteIngredientPagingRepository
        ) {
        this.instance = remoteIngredientPagingRepository.orElse(defaultIngredientPagingRepository);
    }

    @Override
    public IngredientPage findAll(EntityPageable pageable) {
        return instance.findAll(pageable);
    }
}
