package cmahy.webapp.user.adapter.webclient.config.properties.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserExternalResource(
    @NotNull(message = "Must be not null")
    @NotBlank(message = "Must be not blank")
    String baseUrl,
    SslOption ssl
) { }
