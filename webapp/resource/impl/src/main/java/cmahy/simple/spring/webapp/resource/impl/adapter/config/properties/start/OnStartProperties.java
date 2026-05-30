package cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.start;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public record OnStartProperties(
    ResourcesProperties resources
) {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("resources", resources())
            .toString();
    }

}
