package cmahy.simple.spring.webapp.taco.shop.adapter.webclient.config.primary;

import cmahy.simple.spring.webapp.taco.shop.kernel.application.repository.ClientOrderRepository;
import cmahy.simple.spring.webapp.taco.shop.kernel.application.repository.annotation.RemoteRepository;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.ClientOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.condition.NoneNestedConditions;
import org.springframework.context.annotation.*;

@Configuration
public class ClientOrderRepositoryPrimaryConfigurer {

    @Bean
    @Primary
    @Conditional(ClientOrderRepositoryNoPrimaryFound.class)
    public ClientOrderRepository<ClientOrder> clientOrderRepository(
        @RemoteRepository ClientOrderRepository clientOrderRepository
    ) {
        return clientOrderRepository;
    }

    private static final class ClientOrderRepositoryNoPrimaryFound extends NoneNestedConditions {

        ClientOrderRepositoryNoPrimaryFound() {
            super(ConfigurationPhase.REGISTER_BEAN);
        }

        @ConditionalOnSingleCandidate(ClientOrderRepository.class)
        private static final class MissingClientOrderRepositoryPrimary {
            private MissingClientOrderRepositoryPrimary() {}
        }
    }
}
