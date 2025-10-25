package cmahy.simple.spring.webapp.shell.client.impl.adapter.config.properties;

import cmahy.simple.spring.webapp.shell.client.impl.adapter.config.properties.security.SecurityProperties;
import cmahy.simple.spring.webapp.shell.client.impl.adapter.config.properties.taco.TacoProperties;
import cmahy.simple.spring.webapp.shell.client.impl.adapter.config.properties.webclient.WebClientProperties;
import cmahy.simple.spring.webapp.shell.client.impl.application.vo.property.console.ConsolePropertyVo;
import cmahy.simple.spring.webapp.shell.client.impl.application.vo.property.rsocket.RSocketPropertyVo;
import jakarta.validation.Valid;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@ConfigurationProperties(prefix = "application")
@Validated
public record ApplicationProperties(
    @Valid ConsolePropertyVo console,
    @Valid SecurityProperties security,
    @Valid WebClientProperties webClient,
    @Valid TacoProperties taco,
    @Valid RSocketPropertyVo rSocket
) {
}
