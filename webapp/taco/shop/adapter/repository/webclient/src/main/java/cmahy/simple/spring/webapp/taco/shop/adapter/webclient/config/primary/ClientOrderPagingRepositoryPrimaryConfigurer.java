package cmahy.simple.spring.webapp.taco.shop.adapter.webclient.config.primary;

import cmahy.simple.spring.webapp.taco.shop.kernel.application.repository.ClientOrderPagingRepository;
import cmahy.simple.spring.webapp.taco.shop.kernel.application.repository.annotation.RemoteRepository;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.ClientOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.condition.NoneNestedConditions;
import org.springframework.context.annotation.*;

@Configuration
public class ClientOrderPagingRepositoryPrimaryConfigurer {

    @Bean
    @Primary
    @Conditional(ClientOrderPagingRepositoryNoPrimaryFound.class)
    public ClientOrderPagingRepository<ClientOrder> clientOrderPagingRepository(
        @RemoteRepository ClientOrderPagingRepository clientOrderPagingRepository
    ) {
        return clientOrderPagingRepository;
    }

    private static final class ClientOrderPagingRepositoryNoPrimaryFound extends NoneNestedConditions {

        ClientOrderPagingRepositoryNoPrimaryFound() {
            super(ConfigurationPhase.REGISTER_BEAN);
        }

        @ConditionalOnSingleCandidate(ClientOrderPagingRepository.class)
        private static final class MissingClientOrderPagingRepositoryPrimary {
            private MissingClientOrderPagingRepositoryPrimary() {}
        }
    }
}
