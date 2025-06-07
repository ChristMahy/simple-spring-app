package cmahy.simple.spring.webapp.authorization.adapter.config;

import cmahy.simple.spring.security.common.api.rsa.repository.RSAPublicKeyRepository;
import cmahy.simple.spring.security.common.api.rsa.vo.id.PublicKeyId;
import cmahy.simple.spring.security.common.impl.rsa.repository.RSAPublicKeyFileResolverRepositoryImplFactory;
import cmahy.simple.spring.webapp.authorization.adapter.config.properties.ApplicationProperties;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.authorization.authentication.JwtClientAssertionAuthenticationProvider;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import java.security.interfaces.RSAPublicKey;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration(proxyBeanMethods = false)
@EnableCaching
public class AuthorizationServerConfigurer {

    @Bean
    public RSAPublicKeyRepository rsaPublicKeyRepository(
        ResourceLoader resourceLoader,
        ApplicationProperties applicationProperties
    ) {
        return RSAPublicKeyFileResolverRepositoryImplFactory.create(
            resourceLoader, applicationProperties.security()
        );
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationSecurityFilterChain(
        HttpSecurity httpSecurity,
        RSAPublicKeyRepository rsaPublicKeyRepository
    ) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(httpSecurity);

        httpSecurity
            .getConfigurer(OAuth2AuthorizationServerConfigurer.class)
            .oidc(withDefaults())
            .clientAuthentication(clientAuthenticationConfigurer -> {
                clientAuthenticationConfigurer.authenticationProviders(authenticationProviders -> {
                    authenticationProviders.stream()
                        .filter(aP -> aP instanceof JwtClientAssertionAuthenticationProvider)
                        .findFirst()
                        .map(JwtClientAssertionAuthenticationProvider.class::cast)
                        .ifPresent(authenticationProvider -> {
                            authenticationProvider.setJwtDecoderFactory(jwtDecoderFactory(rsaPublicKeyRepository));
                        });
                });
            });

        httpSecurity
            // Redirect to the login page when not authenticated from the
            // authorization endpoint
            .exceptionHandling((exceptions) -> exceptions
                .authenticationEntryPoint(
                    new LoginUrlAuthenticationEntryPoint("/login")
                )
            )
            // Accept access tokens for User Info and/or Client Registration
            .oauth2ResourceServer(withDefaults());

        return httpSecurity
            .formLogin(withDefaults())
            .build();
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().build();
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    private JwtDecoderFactory<RegisteredClient> jwtDecoderFactory(RSAPublicKeyRepository rsaPublicKeyRepository) {
        return registeredClient -> {
            try {
                RSAPublicKey publicKey = rsaPublicKeyRepository.findById(new PublicKeyId(registeredClient.getClientId()));

                return NimbusJwtDecoder
                    .withPublicKey(publicKey)
                    .signatureAlgorithm(SignatureAlgorithm.RS256)
                    .build();
            } catch (Exception ignored) {}

            throw new IllegalArgumentException("Unknown client: " + registeredClient.getClientId());
        };
    }
}
