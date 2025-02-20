package cmahy.simple.spring.webapp.shell.client.impl.adapter.config.properties.ingredient;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SslOption(
    Boolean enabled,
    @NotNull(message = "Must be not null")
    @NotBlank(message = "Must be not blank")
    String bundleName
) {
}
