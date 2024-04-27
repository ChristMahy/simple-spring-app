package cmahy.webapp.resource.impl.adapter.security.oauth2;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = OAuth2Properties.OAUTH2_PROPERTY_PREFIX)
public record OAuth2Properties(
    Boolean enable
) {
    public static final String OAUTH2_PROPERTY_PREFIX = "spring-app.security.oauth2";
}
