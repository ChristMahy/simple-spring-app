package cmahy.webapp.resource.impl.adapter.taco.shop.properties.ingredient;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SslOption(
    Boolean enabled,
    @NotNull(message = "Must be not null")
    @NotBlank(message = "Must be not blank")
    String bundleName
) {
}
