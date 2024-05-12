package cmahy.webapp.resource.ui.taco.vo.input;

import cmahy.webapp.resource.ui.taco.vo.id.IngredientUiId;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Collections;
import java.util.List;

public record TacoInputUiVo(
    @NotNull(message = "Name must be at least 5 characters long")
    @Size(min = 5, message = "Name must be at least 5 characters long")
    String name,
    @NotNull(message = "You must choose at least 1 ingredient")
    @Size(min = 1, message = "You must choose at least 1 ingredient")
    List<IngredientUiId> ingredientIds
) {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("name", name)
            .append(
                "ingredientIds",
                ingredientIds == null ?
                    Collections.emptyList() :
                    ingredientIds.stream().map(IngredientUiId::value).toList()
            )
            .build();
    }

    public static TacoInputUiVo createEmpty() {
        return new TacoInputUiVo("", Collections.emptyList());
    }
}
