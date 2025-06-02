package cmahy.simple.spring.webapp.shell.client.impl.adapter.config.properties.webclient.user;

import cmahy.simple.spring.security.client.api.webclient.repository.WebClientConfigurationRepository;
import jakarta.validation.Valid;

import java.util.Optional;

public record UserWebClientProperties(
    @Valid UserBasicAuthorizationProperties basicAuthorizationCredentials,
    @Valid UserOAuth2AuthorizationProperties oAuth2AuthorizationCredentials
) implements WebClientConfigurationRepository {

    @Override
    public Optional<String> defaultRegistrationId() {
        return Optional.ofNullable(oAuth2AuthorizationCredentials())
            .map(UserOAuth2AuthorizationProperties::registrationId);
    }

}
