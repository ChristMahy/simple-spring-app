package cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.webclient.tacoshop;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public record TacoShopOAuth2AuthorizationProperties(
    String registrationId
) {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("registrationId", registrationId())
            .toString();
    }

}
