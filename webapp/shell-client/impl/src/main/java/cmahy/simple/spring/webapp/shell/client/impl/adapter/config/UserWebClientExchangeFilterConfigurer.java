package cmahy.simple.spring.webapp.shell.client.impl.adapter.config;

import cmahy.simple.spring.security.client.api.webclient.filter.factory.ExchangeFilterAuthorizationHeaderFactory;
import cmahy.simple.spring.security.client.impl.webclient.credential.BasicAuthorizationHeaderAppenderImpl;
import cmahy.simple.spring.security.client.impl.webclient.filter.factory.*;
import cmahy.simple.spring.security.common.api.rsa.repository.RSAPrivateKeyRepository;
import cmahy.simple.spring.webapp.shell.client.impl.adapter.config.properties.ApplicationProperties;
import cmahy.simple.spring.webapp.shell.client.impl.adapter.repository.webclient.UserBasicCredentialRepositoryImpl;
import cmahy.simple.spring.webapp.user.adapter.webclient.annotation.security.UserExchangeFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;

@Configuration
public class UserWebClientExchangeFilterConfigurer {

    @Bean
    @ConditionalOnProperty(value = "application.webclient.user.credential.appender", havingValue = "basic")
    @UserExchangeFilter
    public ExchangeFilterAuthorizationHeaderFactory userExchangeFilterBasicAuthorizationHeaderFactory(
        UserBasicCredentialRepositoryImpl userBasicCredentialRepository
    ) {
        return new ExchangeFilterBasicAuthorizationHeaderFactoryImpl(
            new BasicAuthorizationHeaderAppenderImpl(
                userBasicCredentialRepository
            )
        );
    }

    @Bean
    @ConditionalOnProperty(value = "application.webclient.user.credential.appender", havingValue = "o-auth2")
    @UserExchangeFilter
    public ExchangeFilterAuthorizationHeaderFactory userExchangeFilterClientSecretBasicAuthorizationHeaderFactory(
        ReactiveOAuth2AuthorizedClientManager reactiveOAuth2AuthorizedClientManager,
        ApplicationProperties applicationProperties
    ) {
        return new ServerExchangeFilterClientSecretBasicAuthorizationHeaderFactoryImpl(
            reactiveOAuth2AuthorizedClientManager,
            applicationProperties.webClient().user()
        );
    }

    @Bean
    @ConditionalOnProperty(value = "application.webclient.user.credential.appender", havingValue = "o-auth2-private-key-jwt")
    @UserExchangeFilter
    public ExchangeFilterAuthorizationHeaderFactory userExchangeFilterPrivateKeyJwtAuthorizationHeaderFactory(
        ReactiveClientRegistrationRepository reactiveClientRegistrationRepository,
        ReactiveOAuth2AuthorizedClientService reactiveOAuth2AuthorizedClientService,
        RSAPrivateKeyRepository rsaPrivateKeyRepository,
        ApplicationProperties applicationProperties
    ) {
        return new ServerExchangeFilterPrivateKeyJwtAuthorizationHeaderFactoryImpl(
            reactiveClientRegistrationRepository,
            reactiveOAuth2AuthorizedClientService,
            rsaPrivateKeyRepository,
            applicationProperties.webClient().user()
        );
    }
}
