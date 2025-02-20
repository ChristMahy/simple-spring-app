package cmahy.simple.spring.webapp.taco.shop.adapter.webclient.config.primary;

import cmahy.simple.spring.webapp.taco.shop.kernel.application.repository.IngredientPagingRepository;
import cmahy.simple.spring.webapp.taco.shop.kernel.application.repository.annotation.RemoteRepository;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.Ingredient;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotatedTypeMetadata;

@Configuration
public class IngredientPagingRepositoryPrimaryConfigurer {
    @Bean
    @Primary
    @Conditional(IngredientPagingRepositoryNoPrimaryFound.class)
    public IngredientPagingRepository<Ingredient> ingredientPagingRepository(
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
