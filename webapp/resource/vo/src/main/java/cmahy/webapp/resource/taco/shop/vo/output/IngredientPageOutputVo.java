package cmahy.webapp.resource.taco.shop.vo.output;

import cmahy.common.entity.page.EntityPage;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public record IngredientPageOutputVo(
    List<IngredientOutputVo> content,
    Long totalElements
) implements EntityPage<IngredientOutputVo> {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("content", content())
            .append("totalElements", totalElements())
            .toString();
    }
}
