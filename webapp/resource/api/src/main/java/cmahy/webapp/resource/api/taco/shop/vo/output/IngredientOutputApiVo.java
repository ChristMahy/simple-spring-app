package cmahy.webapp.resource.api.taco.shop.vo.output;

import cmahy.webapp.resource.api.taco.shop.vo.id.IngredientApiId;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public record IngredientOutputApiVo(
    IngredientApiId id,
    String name,
    String type
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
