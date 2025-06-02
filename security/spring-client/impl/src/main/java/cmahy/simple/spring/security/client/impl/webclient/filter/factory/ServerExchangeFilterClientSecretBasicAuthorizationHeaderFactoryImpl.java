package cmahy.simple.spring.security.client.impl.webclient.filter.factory;

import cmahy.simple.spring.security.client.api.webclient.filter.factory.ExchangeFilterAuthorizationHeaderFactory;
import cmahy.simple.spring.security.client.api.webclient.repository.WebClientConfigurationRepository;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;

public class ServerExchangeFilterClientSecretBasicAuthorizationHeaderFactoryImpl implements ExchangeFilterAuthorizationHeaderFactory {

    private final ExchangeFilterFunction exchangeFilterFunction;

    public ServerExchangeFilterClientSecretBasicAuthorizationHeaderFactoryImpl(
        ReactiveOAuth2AuthorizedClientManager authorizedClientManager,
        WebClientConfigurationRepository webClientConfigurationRepository
    ) {

        ServerOAuth2AuthorizedClientExchangeFilterFunction exchangeFilterFunction = new ServerOAuth2AuthorizedClientExchangeFilterFunction(
            authorizedClientManager
        );

        webClientConfigurationRepository
            .defaultRegistrationId()
            .ifPresent(exchangeFilterFunction::setDefaultClientRegistrationId);

        this.exchangeFilterFunction = exchangeFilterFunction;

    }

    @Override
    public ExchangeFilterFunction create() {
        return exchangeFilterFunction;
    }

}
