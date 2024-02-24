package cmahy.webapp.resource.impl.application.taco.shop.vo.input;

import cmahy.webapp.resource.impl.domain.taco.id.IngredientId;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Set;
import java.util.stream.Collectors;

public record TacoInputAppVo(
    String name,
    Set<IngredientId> ingredientIds
) {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("name", name)
            .append("ingredientIds", ingredientIds.stream().map(IngredientId::value).collect(Collectors.toSet()))
            .build();
    }
}
