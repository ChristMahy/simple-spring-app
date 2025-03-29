package cmahy.simple.spring.webapp.resource.impl.adapter.security.config.properties;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = OAuth2Properties.OAUTH2_PROPERTY_PREFIX)
public record OAuth2Properties(
    Map<String, Class<?>> oAuth2ServiceConfigurer,
    Map<String, Class<?>> oidcServiceConfigurer
) {

    public static final String OAUTH2_PROPERTY_PREFIX = "spring-app.security.oauth2";

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("oAuth2ServiceConfigurer", oAuth2ServiceConfigurer())
            .append("oidcServiceConfigurer", oidcServiceConfigurer())
            .toString();
    }
}
