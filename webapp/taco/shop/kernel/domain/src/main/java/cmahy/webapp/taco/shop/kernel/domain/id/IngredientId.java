package cmahy.webapp.taco.shop.kernel.domain.id;

import cmahy.common.entity.id.EntityId;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public record IngredientId(String value) implements EntityId<String> {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("value", value())
            .toString();
    }
}
