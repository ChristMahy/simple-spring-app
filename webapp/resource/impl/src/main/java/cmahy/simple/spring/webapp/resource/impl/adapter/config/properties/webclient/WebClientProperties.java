package cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.webclient;

import cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.webclient.tacoshop.TacoShopWebClientProperties;
import jakarta.validation.Valid;

public record WebClientProperties(
    @Valid TacoShopWebClientProperties tacoShop
) {
}
