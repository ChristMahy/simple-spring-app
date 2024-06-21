package cmahy.webapp.resource.impl.domain.taco.external;

import cmahy.webapp.resource.impl.domain.taco.IngredientType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public record IngredientExternal(
    String id,
    String name,
    IngredientType type
) {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("id", id)
            .append("name", name)
            .append("type", type)
            .toString();
    }
}
