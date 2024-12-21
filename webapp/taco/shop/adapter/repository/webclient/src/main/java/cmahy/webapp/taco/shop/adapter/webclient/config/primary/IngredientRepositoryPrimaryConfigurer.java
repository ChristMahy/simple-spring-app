package cmahy.webapp.taco.shop.adapter.webclient.config.primary;

import cmahy.webapp.taco.shop.kernel.application.repository.IngredientRepository;
import cmahy.webapp.taco.shop.kernel.application.repository.annotation.RemoteRepository;
import cmahy.webapp.taco.shop.kernel.domain.Ingredient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.condition.NoneNestedConditions;
import org.springframework.context.annotation.*;

@Configuration
public class IngredientRepositoryPrimaryConfigurer {

    @Bean
    @Primary
    @Conditional(IngredientRepositoryNoPrimaryFound.class)
    public IngredientRepository<Ingredient> ingredientRepository(
        @RemoteRepository IngredientRepository ingredientRepository
    ) {
        return ingredientRepository;
    }

    private static final class IngredientRepositoryNoPrimaryFound extends NoneNestedConditions {

        IngredientRepositoryNoPrimaryFound() {
            super(ConfigurationPhase.REGISTER_BEAN);
        }

        @ConditionalOnSingleCandidate(IngredientRepository.class)
        private static final class MissingIngredientRepositoryPrimary {
            private MissingIngredientRepositoryPrimary() {}
        }
    }
}
