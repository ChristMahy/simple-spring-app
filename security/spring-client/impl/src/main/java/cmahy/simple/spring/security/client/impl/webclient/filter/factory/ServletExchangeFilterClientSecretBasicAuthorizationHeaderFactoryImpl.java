package cmahy.simple.spring.security.client.impl.webclient.filter.factory;

import cmahy.simple.spring.security.client.api.webclient.filter.factory.ExchangeFilterAuthorizationHeaderFactory;
import cmahy.simple.spring.security.client.api.webclient.repository.WebClientConfigurationRepository;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;

public class ServletExchangeFilterClientSecretBasicAuthorizationHeaderFactoryImpl implements ExchangeFilterAuthorizationHeaderFactory {

    private final ExchangeFilterFunction exchangeFilterFunction;

    public ServletExchangeFilterClientSecretBasicAuthorizationHeaderFactoryImpl(
        OAuth2AuthorizedClientManager authorizedClientManager,
        WebClientConfigurationRepository webClientConfigurationRepository
    ) {

        ServletOAuth2AuthorizedClientExchangeFilterFunction exchangeFilterFunction = new ServletOAuth2AuthorizedClientExchangeFilterFunction(
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
