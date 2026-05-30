package cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.webclient.tacoshop;

import cmahy.simple.spring.security.client.api.webclient.repository.WebClientConfigurationRepository;
import jakarta.validation.Valid;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("basicAuthorizationCredentials", basicAuthorizationCredentials())
            .append("oAuth2AuthorizationCredentials", oAuth2AuthorizationCredentials())
            .toString();
    }
}
