package cmahy.simple.spring.webapp.resource.impl.adapter.security.config.oauth2;

import cmahy.simple.spring.security.common.api.rsa.repository.RSAPrivateKeyRepository;
import cmahy.simple.spring.security.common.impl.rsa.repository.NormalizedKeyResolverRepository;
import cmahy.simple.spring.security.common.impl.rsa.repository.RSAPrivateKeyFileResolverRepositoryImpl;
import cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.ApplicationProperties;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.server.resource.authentication.*;

@Configuration
public class OAuth2WebClientConfigurer {

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();

        authoritiesConverter.setAuthoritiesClaimName("resources_authorities");

        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);

        return converter;
    }

    @Bean
    public AuthenticationManagerResolver<HttpServletRequest> authenticationManagerResolver(
        JwtAuthenticationConverter jwtAuthenticationConverter
    ) {
        return new JwtIssuerAuthenticationManagerResolver(issuer -> {
            JwtDecoder jwtDecoder = JwtDecoders.fromIssuerLocation(issuer);

            JwtAuthenticationProvider jwtAuthenticationProvider = new JwtAuthenticationProvider(jwtDecoder);

            jwtAuthenticationProvider.setJwtAuthenticationConverter(jwtAuthenticationConverter);

            return new ProviderManager(jwtAuthenticationProvider);
        });

    }

    @Bean
    @ConditionalOnExpression(
        """
            '${application.taco-shop.webclient.enabled}' eq 'true' and
            (
                '${application.webclient.taco-shop.credential.appender:}' eq 'o-auth2'
            )
        """
    )
    public OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager(
        ClientRegistrationRepository clientRegistrationRepository,
        OAuth2AuthorizedClientService authorizedClientService
    ) {
        return new AuthorizedClientServiceOAuth2AuthorizedClientManager(
            clientRegistrationRepository, authorizedClientService
        );
    }

    @Bean
    @ConditionalOnExpression(
        """
            '${application.taco-shop.webclient.enabled}' eq 'true' and
            (
                '${application.webclient.taco-shop.credential.appender:}' eq 'o-auth2-private-key-jwt'
            )
        """
    )
    public RSAPrivateKeyRepository rsaPrivateKeyRepository(
        ResourceLoader resourceLoader,
        ApplicationProperties applicationProperties
    ) {
        return new RSAPrivateKeyFileResolverRepositoryImpl(
            new NormalizedKeyResolverRepository(
                resourceLoader
            ),
            applicationProperties.security()
        );
    }

}
