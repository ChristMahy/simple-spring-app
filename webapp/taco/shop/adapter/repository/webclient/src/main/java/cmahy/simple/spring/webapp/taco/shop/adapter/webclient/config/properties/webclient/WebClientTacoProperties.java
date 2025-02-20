package cmahy.simple.spring.webapp.taco.shop.adapter.webclient.config.properties.webclient;

import jakarta.validation.Valid;

public record WebClientTacoProperties(
    @Valid WebClientTacoCredentialProperties credentials
) {
}
