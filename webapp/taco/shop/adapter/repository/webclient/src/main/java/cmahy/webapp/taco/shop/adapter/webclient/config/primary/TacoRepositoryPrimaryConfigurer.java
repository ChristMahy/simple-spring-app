package cmahy.webapp.taco.shop.adapter.webclient.config.primary;

import cmahy.webapp.taco.shop.kernel.application.repository.TacoRepository;
import cmahy.webapp.taco.shop.kernel.application.repository.annotation.RemoteRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.condition.NoneNestedConditions;
import org.springframework.context.annotation.*;

@Configuration
public class TacoRepositoryPrimaryConfigurer {

    @Bean
    @Primary
    @Conditional(TacoRepositoryNoPrimaryFound.class)
    public TacoRepository tacoRepository(
        @RemoteRepository TacoRepository tacoRepository
    ) {
        return tacoRepository;
    }

    private static final class TacoRepositoryNoPrimaryFound extends NoneNestedConditions {

        TacoRepositoryNoPrimaryFound() {
            super(ConfigurationPhase.REGISTER_BEAN);
        }

        @ConditionalOnSingleCandidate(TacoRepository.class)
        private static final class MissingTacoRepositoryPrimary {
            private MissingTacoRepositoryPrimary() {}
        }
    }
}
