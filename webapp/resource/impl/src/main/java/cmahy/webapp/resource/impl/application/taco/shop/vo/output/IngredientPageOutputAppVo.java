package cmahy.webapp.resource.impl.application.taco.shop.vo.output;

import cmahy.common.entity.page.EntityPage;
import cmahy.webapp.resource.impl.domain.taco.id.IngredientId;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public record IngredientPageOutputAppVo(
    List<IngredientOutputAppVo> content,
    Long totalElements
) implements EntityPage<IngredientOutputAppVo> {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("content", content())
            .append("totalElements", totalElements())
            .toString();
    }
}
