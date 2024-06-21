package cmahy.webapp.resource.impl.domain.taco.external.page;

import cmahy.common.entity.page.EntityPage;
import cmahy.webapp.resource.impl.domain.taco.Ingredient;
import cmahy.webapp.resource.impl.domain.taco.external.IngredientExternal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Collection;

public record IngredientExternalPage(
    Collection<IngredientExternal> content,
    Long totalElements
) implements EntityPage<Ingredient> {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("content", content)
            .append("totalElements", totalElements)
            .toString();
    }
}
