package cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.start;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.core.io.Resource;

public record ResourcesProperties(
    Resource rights,
    Resource roles,
    Resource users,
    Resource usersSecurities,
    Resource ingredients,
    Resource clientsOrders
) {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("rights", rights())
            .append("roles", roles())
            .append("users", users())
            .append("usersSecurities", usersSecurities())
            .append("ingredients", ingredients())
            .append("clientsOrders", clientsOrders())
            .toString();
    }

}
