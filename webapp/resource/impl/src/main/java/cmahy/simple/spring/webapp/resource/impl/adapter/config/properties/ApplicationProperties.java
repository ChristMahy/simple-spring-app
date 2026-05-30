package cmahy.simple.spring.webapp.resource.impl.adapter.config.properties;

import cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.security.SecurityProperties;
import cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.start.OnStartProperties;
import cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.webclient.WebClientProperties;
import jakarta.validation.Valid;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "application")
@Validated
public record ApplicationProperties(
    @Valid WebClientProperties webClient,
    @Valid SecurityProperties security,
    OnStartProperties onStart
) {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("webClient", webClient())
            .append("security", security())
            .append("onStart", onStart())
            .toString();
    }

}
