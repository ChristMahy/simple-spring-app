package cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.webclient;

import cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.webclient.tacoshop.TacoShopWebClientProperties;
import jakarta.validation.Valid;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public record WebClientProperties(
    @Valid TacoShopWebClientProperties tacoShop
) {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("tacoShop", tacoShop())
            .toString();
    }

}
