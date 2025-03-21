package cmahy.simple.spring.webapp.resource.impl.adapter.security.config.properties;

import jakarta.validation.constraints.NotBlank;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = GoogleProperties.GOOGLE_PROPERTY_PREFIX)
public record GoogleProperties(
    @NotBlank String apiUrl
) {

    public static final String GOOGLE_PROPERTY_PREFIX = "application.google";

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("apiUrl", apiUrl)
            .toString();
    }
}
