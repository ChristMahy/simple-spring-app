package cmahy.webapp.resource.impl.application.taco.shop.vo.output;

import cmahy.webapp.resource.impl.domain.taco.id.IngredientId;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public record IngredientOutputAppVo(
    IngredientId id,
    String name,
    String type
) {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("id", id())
            .append("name", name())
            .append("type", type())
            .toString();
    }
}
