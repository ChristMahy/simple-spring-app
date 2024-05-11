package cmahy.webapp.resource.impl.adapter.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "application.password")
public record PasswordProperties(
    String defaultEncoder,
    Map<String, String> encoders
) {
}
