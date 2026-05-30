package cmahy.simple.spring.webapp.resource.impl.adapter.startup.generator.taco.vo.input;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public record TacoGeneratorInputVo(
    String name,
    List<IngredientGeneratorInputVo> ingredients
) {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("name", name())
            .append("ingredients", ingredients())
            .toString();
    }

}
