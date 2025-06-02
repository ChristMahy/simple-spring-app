package cmahy.simple.spring.security.client.impl.webclient.filter.factory;

import cmahy.simple.spring.security.client.api.webclient.filter.factory.ExchangeFilterAuthorizationHeaderFactory;
import cmahy.simple.spring.security.client.api.webclient.repository.WebClientConfigurationRepository;
import cmahy.simple.spring.security.client.impl.webclient.jwt.RSAPrivateKeyJwtRequestParametersConverter;
import cmahy.simple.spring.security.common.api.rsa.repository.RSAPrivateKeyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.endpoint.RestClientClientCredentialsTokenResponseClient;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;

public class ServletExchangeFilterPrivateKeyJwtAuthorizationHeaderFactoryImpl implements ExchangeFilterAuthorizationHeaderFactory {

    private static final Logger LOG = LoggerFactory.getLogger(ServletExchangeFilterPrivateKeyJwtAuthorizationHeaderFactoryImpl.class);

    private final ExchangeFilterFunction exchangeFilterFunction;

    public ServletExchangeFilterPrivateKeyJwtAuthorizationHeaderFactoryImpl(
        ClientRegistrationRepository reactiveClientRegistrationRepository,
        OAuth2AuthorizedClientService reactiveOAuth2AuthorizedClientService,
        RSAPrivateKeyRepository rsaPrivateKeyRepository,
        WebClientConfigurationRepository webClientConfigurationRepository
    ) {

        AuthorizedClientServiceOAuth2AuthorizedClientManager clientManager = new AuthorizedClientServiceOAuth2AuthorizedClientManager(
            reactiveClientRegistrationRepository,
            reactiveOAuth2AuthorizedClientService
        );

        ClientCredentialsOAuth2AuthorizedClientProvider clientProvider = new ClientCredentialsOAuth2AuthorizedClientProvider();
        RestClientClientCredentialsTokenResponseClient tokenResponseClient = new RestClientClientCredentialsTokenResponseClient();
        ServletOAuth2AuthorizedClientExchangeFilterFunction exchangeFilterFunction = new ServletOAuth2AuthorizedClientExchangeFilterFunction(clientManager);

        tokenResponseClient.setParametersConverter(new RSAPrivateKeyJwtRequestParametersConverter(rsaPrivateKeyRepository));
        clientProvider.setAccessTokenResponseClient(tokenResponseClient);
        clientManager.setAuthorizedClientProvider(clientProvider);

        webClientConfigurationRepository
            .defaultRegistrationId()
            .ifPresent(exchangeFilterFunction::setDefaultClientRegistrationId);

        this.exchangeFilterFunction = exchangeFilterFunction;
    }

    @Override
    public ExchangeFilterFunction create() {
        return this.exchangeFilterFunction;
    }

}
