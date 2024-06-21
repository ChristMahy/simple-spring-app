package cmahy.webapp.resource.impl.adapter.config.properties.webclient;

import jakarta.validation.Valid;

public record WebClientTacoProperties(
    @Valid WebClientTacoCredentialProperties credentials
) {
}
