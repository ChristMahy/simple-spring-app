package cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.webclient.tacoshop;

import cmahy.simple.spring.security.client.api.webclient.repository.WebClientConfigurationRepository;
import jakarta.validation.Valid;

import java.util.Optional;

public record TacoShopWebClientProperties(
    @Valid TacoShopBasicAuthorizationProperties basicAuthorizationCredentials,
    @Valid TacoShopOAuth2AuthorizationProperties oAuth2AuthorizationCredentials
) implements WebClientConfigurationRepository {

    @Override
    public Optional<String> defaultRegistrationId() {
        return Optional.ofNullable(oAuth2AuthorizationCredentials())
            .map(TacoShopOAuth2AuthorizationProperties::registrationId);
    }

}
