package cmahy.simple.spring.webapp.taco.shop.adapter.webclient.config.properties.webclient;

import jakarta.validation.constraints.NotNull;

import java.time.Duration;

public record TacoShopWebClientCommonProperties(
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
