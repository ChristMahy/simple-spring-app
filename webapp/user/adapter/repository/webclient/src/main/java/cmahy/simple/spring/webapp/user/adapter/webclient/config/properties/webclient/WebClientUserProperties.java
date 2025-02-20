package cmahy.simple.spring.webapp.user.adapter.webclient.config.properties.webclient;

import jakarta.validation.Valid;

public record WebClientUserProperties(
    @Valid WebClientUserCredentialProperties credentials
) {
}
