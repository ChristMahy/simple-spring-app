package cmahy.simple.spring.webapp.resource.impl.adapter.startup.generator.taco.vo.input;

import cmahy.simple.spring.webapp.taco.shop.kernel.domain.IngredientType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public record IngredientGeneratorInputVo(
    String name,
    IngredientType type
) {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("name", name())
            .append("type", type())
            .toString();
    }

}
