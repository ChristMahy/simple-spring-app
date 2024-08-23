package cmahy.webapp.shell.client.impl.adapter.config.properties.webclient;

import jakarta.validation.constraints.NotNull;

import java.time.Duration;

public record WebClientCommonProperties(
    @NotNull
    Duration connectTimeout,
    @NotNull
    Duration responseTimeout,
    @NotNull
    Duration readTimeout,
    @NotNull
    Duration writeTimeout
) {
}
