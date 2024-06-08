package cmahy.webapp.resource.api.taco.shop.vo.output;

import cmahy.common.entity.page.EntityPage;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public record IngredientPageOutputApiVo(
    List<IngredientOutputApiVo> content,
    Long totalElements
) implements EntityPage<IngredientOutputApiVo> {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("content", content)
            .append("totalElements", totalElements)
            .toString();
    }
}
