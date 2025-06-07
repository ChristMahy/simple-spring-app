package cmahy.simple.spring.security.client.impl.webclient.filter.factory;

import cmahy.simple.spring.security.client.api.webclient.repository.WebClientConfigurationRepository;
import cmahy.simple.spring.security.common.api.rsa.repository.RSAPrivateKeyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
class ServletExchangeFilterPrivateKeyJwtAuthorizationHeaderFactoryImplTest {

    @Mock
    private ClientRegistrationRepository reactiveClientRegistrationRepository;

    @Mock
    private OAuth2AuthorizedClientService reactiveOAuth2AuthorizedClientService;

    @Mock
    private RSAPrivateKeyRepository rsaPrivateKeyRepository;

    @Mock
    private WebClientConfigurationRepository webClientConfigurationRepository;

    @InjectMocks
    private ServletExchangeFilterPrivateKeyJwtAuthorizationHeaderFactoryImpl servletExchangeFilterPrivateKeyJwtAuthorizationHeaderFactoryImpl;

    @Test
    void create() {
        assertDoesNotThrow(() -> {


            ExchangeFilterFunction exchangeFilterFunction = servletExchangeFilterPrivateKeyJwtAuthorizationHeaderFactoryImpl.create();


            assertThat(exchangeFilterFunction).isNotNull();
        });
    }
}