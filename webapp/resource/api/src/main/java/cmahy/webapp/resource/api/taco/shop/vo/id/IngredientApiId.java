package cmahy.webapp.resource.api.taco.shop.vo.id;

import cmahy.common.entity.id.EntityId;
import org.apache.commons.lang3.builder.ToStringBuilder;

public record IngredientApiId(String value) implements EntityId<String> {

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("value", value())
            .toString();
    }
}
