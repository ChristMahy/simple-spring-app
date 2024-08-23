package cmahy.webapp.taco.shop.kernel.vo.input;

import cmahy.webapp.taco.shop.kernel.domain.id.IngredientId;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public record TacoInputVo(
    @NotNull(message = "Name must be at least 5 characters long")
    @Size(min = 5, message = "Name must be at least 5 characters long")
    String name,
    @NotNull(message = "You must choose at least 1 ingredient")
    @Size(min = 1, message = "You must choose at least 1 ingredient")
    Set<IngredientId> ingredientIds
) {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("name", name())
            .append("ingredientIds", Objects.nonNull(ingredientIds()) ? ingredientIds().stream().map(IngredientId::value).collect(Collectors.toSet()) : null)
            .build();
    }
}
