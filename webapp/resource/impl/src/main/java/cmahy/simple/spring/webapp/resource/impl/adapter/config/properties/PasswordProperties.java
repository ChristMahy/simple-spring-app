package cmahy.simple.spring.webapp.resource.impl.adapter.config.properties;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "application.password")
public record PasswordProperties(
    String defaultEncoder,
    Map<String, String> encoders
) {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("defaultEncoder", defaultEncoder())
            .append("encoders", encoders())
            .toString();
    }

}
