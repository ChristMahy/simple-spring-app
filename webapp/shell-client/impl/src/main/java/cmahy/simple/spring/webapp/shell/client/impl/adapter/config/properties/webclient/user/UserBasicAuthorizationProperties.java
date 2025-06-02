package cmahy.simple.spring.webapp.shell.client.impl.adapter.config.properties.webclient.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public record UserBasicAuthorizationProperties(
    @NotNull(message = "Shouldn't be null")
    @NotEmpty(message = "Shouldn't be empty")
    byte[] username,
    @NotNull(message = "Shouldn't be null")
    @NotEmpty(message = "Shouldn't be empty")
    byte[] password
) {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("username", username())
            .toString();
    }
}
