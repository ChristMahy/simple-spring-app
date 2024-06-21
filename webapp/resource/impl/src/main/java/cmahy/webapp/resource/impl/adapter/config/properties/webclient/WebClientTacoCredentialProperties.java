package cmahy.webapp.resource.impl.adapter.config.properties.webclient;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record WebClientTacoCredentialProperties(
    @NotNull(message = "Shouldn't be null")
    @NotEmpty(message = "Shouldn't be empty")
    byte[] username,
    @NotNull(message = "Shouldn't be null")
    @NotEmpty(message = "Shouldn't be empty")
    byte[] password
) {
}
