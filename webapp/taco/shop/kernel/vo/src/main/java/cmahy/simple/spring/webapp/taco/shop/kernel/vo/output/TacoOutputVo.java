package cmahy.simple.spring.webapp.taco.shop.kernel.vo.output;

import cmahy.simple.spring.webapp.taco.shop.kernel.domain.id.TacoId;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

public record TacoOutputVo(
    TacoId id,
    Date createdAt,
    String name,
    List<IngredientOutputVo> ingredients
) {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("id", id())
            .append("createdAt", createdAt())
            .append("name", name())
            .append("ingredients", ingredients())
            .toString();
    }
}
