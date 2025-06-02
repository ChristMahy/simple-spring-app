package cmahy.simple.spring.webapp.authorization.adapter.config.properties;

import cmahy.simple.spring.webapp.authorization.adapter.config.properties.security.SecurityProperties;
import jakarta.validation.Valid;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "application")
public record ApplicationProperties(
    @Valid SecurityProperties security
) {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("security", security())
            .toString();
    }

}
