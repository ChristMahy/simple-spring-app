package cmahy.simple.spring.webapp.shell.client.impl.adapter.config.properties.webclient;

import jakarta.validation.Valid;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "application.web-client")
public record WebClientProperties(
    @Valid WebClientCommonProperties common,
    @Valid WebClientTacoProperties taco
) {
}
