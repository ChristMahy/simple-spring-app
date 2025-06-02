package cmahy.simple.spring.webapp.shell.client.impl.adapter.config.properties.webclient;

import cmahy.simple.spring.webapp.shell.client.impl.adapter.config.properties.webclient.tacoshop.TacoShopWebClientProperties;
import cmahy.simple.spring.webapp.shell.client.impl.adapter.config.properties.webclient.user.UserWebClientProperties;
import jakarta.validation.Valid;

public record WebClientProperties(
    @Valid UserWebClientProperties user,
    @Valid TacoShopWebClientProperties tacoShop
) {
}
