package cmahy.simple.spring.webapp.resource.impl.adapter.config.properties;

import cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.security.SecurityProperties;
import cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.webclient.WebClientProperties;
import jakarta.validation.Valid;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "application")
@Validated
public record ApplicationProperties(
    @Valid WebClientProperties webClient,
    @Valid SecurityProperties security
) {
}
