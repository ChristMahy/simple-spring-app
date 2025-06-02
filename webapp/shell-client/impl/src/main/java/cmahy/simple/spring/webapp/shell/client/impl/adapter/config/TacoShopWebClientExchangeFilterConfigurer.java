package cmahy.simple.spring.webapp.shell.client.impl.adapter.config;

import cmahy.simple.spring.security.client.api.webclient.filter.factory.ExchangeFilterAuthorizationHeaderFactory;
import cmahy.simple.spring.security.client.impl.webclient.credential.BasicAuthorizationHeaderAppenderImpl;
import cmahy.simple.spring.security.client.impl.webclient.filter.factory.*;
import cmahy.simple.spring.security.common.api.rsa.repository.RSAPrivateKeyRepository;
import cmahy.simple.spring.webapp.shell.client.impl.adapter.config.properties.ApplicationProperties;
import cmahy.simple.spring.webapp.shell.client.impl.adapter.repository.webclient.TacoShopBasicCredentialRepositoryImpl;
import cmahy.simple.spring.webapp.taco.shop.adapter.webclient.annotation.security.TacoShopExchangeFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;

@Configuration
public class TacoShopWebClientExchangeFilterConfigurer {

    @Bean
    @ConditionalOnProperty(value = "application.webclient.taco-shop.credential.appender", havingValue = "basic")
    @TacoShopExchangeFilter
    public ExchangeFilterAuthorizationHeaderFactory tacoShopExchangeFilterBasicAuthorizationHeaderFactory(
        TacoShopBasicCredentialRepositoryImpl tacoShopBasicCredentialRepository
    ) {
        return new ExchangeFilterBasicAuthorizationHeaderFactoryImpl(
            new BasicAuthorizationHeaderAppenderImpl(
                tacoShopBasicCredentialRepository
            )
        );
    }

    @Bean
    @ConditionalOnProperty(value = "application.webclient.taco-shop.credential.appender", havingValue = "o-auth2")
    @TacoShopExchangeFilter
    public ExchangeFilterAuthorizationHeaderFactory tacoShopExchangeFilterClientSecretBasicAuthorizationHeaderFactory(
        ReactiveOAuth2AuthorizedClientManager reactiveOAuth2AuthorizedClientManager,
        ApplicationProperties applicationProperties
    ) {
        return new ServerExchangeFilterClientSecretBasicAuthorizationHeaderFactoryImpl(
            reactiveOAuth2AuthorizedClientManager,
            applicationProperties.webClient().tacoShop()
        );
    }

    @Bean
    @ConditionalOnProperty(value = "application.webclient.taco-shop.credential.appender", havingValue = "o-auth2-private-key-jwt")
    @TacoShopExchangeFilter
    public ExchangeFilterAuthorizationHeaderFactory tacoShopExchangeFilterPrivateKeyJwtAuthorizationHeaderFactory(
        ReactiveClientRegistrationRepository reactiveClientRegistrationRepository,
        ReactiveOAuth2AuthorizedClientService reactiveOAuth2AuthorizedClientService,
        RSAPrivateKeyRepository rsaPrivateKeyRepository,
        ApplicationProperties applicationProperties
    ) {
        return new ServerExchangeFilterPrivateKeyJwtAuthorizationHeaderFactoryImpl(
            reactiveClientRegistrationRepository,
            reactiveOAuth2AuthorizedClientService,
            rsaPrivateKeyRepository,
            applicationProperties.webClient().tacoShop()
        );
    }
}
