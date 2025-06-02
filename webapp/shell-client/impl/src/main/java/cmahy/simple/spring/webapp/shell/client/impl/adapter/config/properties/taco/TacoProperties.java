package cmahy.simple.spring.webapp.shell.client.impl.adapter.config.properties.taco;

import cmahy.simple.spring.webapp.shell.client.impl.adapter.config.properties.taco.ingredient.IngredientProperties;
import jakarta.validation.Valid;

public record TacoProperties(
    @Valid IngredientProperties ingredients
) {
}
