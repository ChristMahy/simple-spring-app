package cmahy.webapp.user.adapter.webclient.config.properties.user;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "application.user")
public record UserProperties(
    UserExternalResource externalResource
) {
}
