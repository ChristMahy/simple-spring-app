package cmahy.webapp.resource.ui.taco.vo.input;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Collection;

public record TacoInputApiVo(
    @NotNull
    @Size(min = 5, message = "Name must be at least 5 characters long")
    String name,
    @Size(min = 1, message = "You must choose at least 1 ingredient")
    Collection<IngredientInputApiVo> ingredients
) {
}
