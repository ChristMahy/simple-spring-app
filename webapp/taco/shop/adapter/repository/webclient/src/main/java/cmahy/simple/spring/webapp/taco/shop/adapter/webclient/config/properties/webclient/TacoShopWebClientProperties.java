package cmahy.simple.spring.webapp.taco.shop.adapter.webclient.config.properties.webclient;

import jakarta.validation.Valid;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "application.web-client")
public record TacoShopWebClientProperties(
    @Valid TacoShopWebClientCommonProperties common
) {
}
