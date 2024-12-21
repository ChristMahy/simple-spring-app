package cmahy.webapp.taco.shop.kernel.domain.page;

import cmahy.common.entity.page.EntityPage;
import cmahy.webapp.taco.shop.kernel.domain.Ingredient;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Collection;

public record IngredientPage<I extends Ingredient>(
    Collection<I> content,
    Long totalElements
) implements EntityPage<I> {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("content", content)
            .append("totalElements", totalElements)
            .toString();
    }
}
