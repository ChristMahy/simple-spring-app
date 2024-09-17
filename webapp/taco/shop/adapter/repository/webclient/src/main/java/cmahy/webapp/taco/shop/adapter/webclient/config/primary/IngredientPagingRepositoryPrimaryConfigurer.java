package cmahy.webapp.taco.shop.adapter.webclient.config.primary;

import cmahy.webapp.taco.shop.kernel.application.repository.IngredientPagingRepository;
import cmahy.webapp.taco.shop.kernel.application.repository.annotation.RemoteRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.condition.NoneNestedConditions;
import org.springframework.context.annotation.*;

@Configuration
public class IngredientPagingRepositoryPrimaryConfigurer {
    @Bean
    @Primary
    @Conditional(IngredientPagingRepositoryNoPrimaryFound.class)
    public IngredientPagingRepository ingredientPagingRepository(
        @RemoteRepository IngredientPagingRepository ingredientPagingRepository
    ) {
        return ingredientPagingRepository;
    }

    private static final class IngredientPagingRepositoryNoPrimaryFound extends NoneNestedConditions {

        IngredientPagingRepositoryNoPrimaryFound() {
            super(ConfigurationPhase.REGISTER_BEAN);
        }

        @ConditionalOnSingleCandidate(IngredientPagingRepository.class)
        private static final class MissingIngredientPagingRepositoryPrimary {
            private MissingIngredientPagingRepositoryPrimary() {}
        }
    }
}
