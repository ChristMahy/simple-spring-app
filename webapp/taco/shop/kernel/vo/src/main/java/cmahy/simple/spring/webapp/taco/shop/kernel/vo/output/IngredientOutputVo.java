package cmahy.simple.spring.webapp.taco.shop.kernel.vo.output;

import cmahy.simple.spring.webapp.taco.shop.kernel.domain.id.IngredientId;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public record IngredientOutputVo(
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
