package cmahy.simple.spring.webapp.resource.impl.adapter.security.config.oauth2;

import cmahy.simple.spring.security.client.api.webclient.filter.factory.ExchangeFilterAuthorizationHeaderFactory;
import cmahy.simple.spring.security.client.impl.webclient.credential.BasicAuthorizationHeaderAppenderImpl;
import cmahy.simple.spring.security.client.impl.webclient.filter.factory.*;
import cmahy.simple.spring.security.common.api.rsa.repository.RSAPrivateKeyRepository;
import cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.ApplicationProperties;
import cmahy.simple.spring.webapp.resource.impl.adapter.security.repository.webclient.TacoShopBasicCredentialRepositoryImpl;
import cmahy.simple.spring.webapp.taco.shop.adapter.webclient.annotation.security.TacoShopExchangeFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;


@Configuration
@ConditionalOnProperty(name = "application.taco-shop.webclient.enabled", havingValue = "true")
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
        OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager,
        ApplicationProperties applicationProperties
    ) {
        return new ServletExchangeFilterClientSecretBasicAuthorizationHeaderFactoryImpl(
            oAuth2AuthorizedClientManager,
            applicationProperties.webClient().tacoShop()
        );
    }

    @Bean
    @ConditionalOnProperty(value = "application.webclient.taco-shop.credential.appender", havingValue = "o-auth2-private-key-jwt")
    @TacoShopExchangeFilter
    public ExchangeFilterAuthorizationHeaderFactory tacoShopExchangeFilterPrivateKeyJwtAuthorizationHeaderFactory(
        ClientRegistrationRepository clientRegistrationRepository,
        OAuth2AuthorizedClientService oAuth2AuthorizedClientService,
        RSAPrivateKeyRepository rsaPrivateKeyRepository,
        ApplicationProperties applicationProperties
    ) {
        return new ServletExchangeFilterPrivateKeyJwtAuthorizationHeaderFactoryImpl(
            clientRegistrationRepository,
            oAuth2AuthorizedClientService,
            rsaPrivateKeyRepository,
            applicationProperties.webClient().tacoShop()
        );
    }

}
