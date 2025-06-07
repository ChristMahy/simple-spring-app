package cmahy.simple.spring.security.client.impl.webclient.filter.factory;

import cmahy.simple.spring.security.client.api.webclient.repository.WebClientConfigurationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
class ServletExchangeFilterClientSecretBasicAuthorizationHeaderFactoryImplTest {

    @Mock
    private OAuth2AuthorizedClientManager authorizedClientManager;

    @Mock
    private WebClientConfigurationRepository webClientConfigurationRepository;

    @InjectMocks
    private ServletExchangeFilterClientSecretBasicAuthorizationHeaderFactoryImpl servletExchangeFilterClientSecretBasicAuthorizationHeaderFactoryImpl;

    @Test
    void create() {
        assertDoesNotThrow(() -> {


            ExchangeFilterFunction exchangeFilterFunction = servletExchangeFilterClientSecretBasicAuthorizationHeaderFactoryImpl.create();


            assertThat(exchangeFilterFunction).isNotNull();
        });
    }
}