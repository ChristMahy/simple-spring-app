package cmahy.simple.spring.security.client.impl.webclient.filter.factory;

import cmahy.simple.spring.security.common.api.rsa.repository.RSAPrivateKeyRepository;
import cmahy.simple.spring.security.client.api.webclient.filter.factory.ExchangeFilterAuthorizationHeaderFactory;
import cmahy.simple.spring.security.client.api.webclient.repository.WebClientConfigurationRepository;
import cmahy.simple.spring.security.client.impl.webclient.jwt.RSAPrivateKeyJwtRequestParametersConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.endpoint.WebClientReactiveClientCredentialsTokenResponseClient;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;

public class ServerExchangeFilterPrivateKeyJwtAuthorizationHeaderFactoryImpl implements ExchangeFilterAuthorizationHeaderFactory {

    private static final Logger LOG = LoggerFactory.getLogger(ServerExchangeFilterPrivateKeyJwtAuthorizationHeaderFactoryImpl.class);

    private final ExchangeFilterFunction exchangeFilterFunction;

    public ServerExchangeFilterPrivateKeyJwtAuthorizationHeaderFactoryImpl(
        ReactiveClientRegistrationRepository reactiveClientRegistrationRepository,
        ReactiveOAuth2AuthorizedClientService reactiveOAuth2AuthorizedClientService,
        RSAPrivateKeyRepository rsaPrivateKeyRepository,
        WebClientConfigurationRepository webClientConfigurationRepository
    ) {

        AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager clientManager = new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(
            reactiveClientRegistrationRepository,
            reactiveOAuth2AuthorizedClientService
        );

        ClientCredentialsReactiveOAuth2AuthorizedClientProvider clientProvider = new ClientCredentialsReactiveOAuth2AuthorizedClientProvider();
        WebClientReactiveClientCredentialsTokenResponseClient tokenResponseClient = new WebClientReactiveClientCredentialsTokenResponseClient();
        ServerOAuth2AuthorizedClientExchangeFilterFunction exchangeFilterFunction = new ServerOAuth2AuthorizedClientExchangeFilterFunction(clientManager);

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
