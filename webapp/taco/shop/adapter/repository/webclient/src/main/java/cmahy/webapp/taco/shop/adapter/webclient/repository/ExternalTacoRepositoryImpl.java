package cmahy.webapp.taco.shop.adapter.webclient.repository;

import cmahy.webapp.taco.shop.adapter.webclient.entity.domain.ExternalTaco;
import cmahy.webapp.taco.shop.kernel.application.repository.TacoRepository;
import cmahy.webapp.taco.shop.kernel.application.repository.annotation.RemoteRepository;
import cmahy.webapp.taco.shop.kernel.domain.id.IngredientId;
import jakarta.inject.Named;

import java.util.Set;

@Named
@RemoteRepository
public class ExternalTacoRepositoryImpl implements TacoRepository<ExternalTaco> {

    @Override
    public ExternalTaco save(ExternalTaco taco) {
        throw new IllegalStateException("Not yet implemented !");
    }

    @Override
    public Set<ExternalTaco> findByIngredientId(IngredientId ingredientId) {
        throw new IllegalStateException("Not yet implemented !");
    }
}
