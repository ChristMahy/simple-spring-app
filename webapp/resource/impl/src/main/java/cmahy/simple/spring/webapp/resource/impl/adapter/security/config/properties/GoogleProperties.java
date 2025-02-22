package cmahy.simple.spring.webapp.resource.impl.adapter.security.config.properties;

import jakarta.validation.constraints.NotBlank;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "application.google")
public record GoogleProperties(
    @NotBlank String apiUrl
) {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("apiUrl", apiUrl)
            .toString();
    }
}
