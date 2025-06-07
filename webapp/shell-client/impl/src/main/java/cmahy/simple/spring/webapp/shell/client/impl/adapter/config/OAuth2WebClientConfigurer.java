package cmahy.simple.spring.webapp.shell.client.impl.adapter.config;

import cmahy.simple.spring.security.common.api.rsa.repository.RSAPrivateKeyRepository;
import cmahy.simple.spring.security.common.impl.rsa.repository.RSAPrivateKeyFileResolverRepositoryImplFactory;
import cmahy.simple.spring.webapp.shell.client.impl.adapter.config.properties.ApplicationProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;

@Configuration
public class OAuth2WebClientConfigurer {

    @Bean
    @ConditionalOnExpression(
        """
            '${application.webclient.taco-shop.credential.appender:}' eq 'o-auth2' or
            '${application.webclient.user.credential.appender:}' eq 'o-auth2'
        """
    )
    public ReactiveOAuth2AuthorizedClientManager oAuth2AuthorizedClientManager(
        ReactiveClientRegistrationRepository clientRegistrationRepository,
        ReactiveOAuth2AuthorizedClientService authorizedClientService
    ) {
        return new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(
            clientRegistrationRepository, authorizedClientService
        );
    }

    @Bean
    @ConditionalOnExpression(
        """
            '${application.webclient.taco-shop.credential.appender:}' eq 'o-auth2-private-key-jwt' or
            '${application.webclient.user.credential.appender:}' eq 'o-auth2-private-key-jwt'
        """
    )
    public RSAPrivateKeyRepository rsaPrivateKeyRepository(
        ResourceLoader resourceLoader,
        ApplicationProperties applicationProperties
    ) {
        return RSAPrivateKeyFileResolverRepositoryImplFactory.create(
            resourceLoader, applicationProperties.security()
        );
    }

}
