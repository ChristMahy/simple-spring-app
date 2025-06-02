package cmahy.simple.spring.webapp.shell.client.impl.adapter.config.properties.taco.ingredient;

import jakarta.validation.Valid;

public record IngredientProperties(
    @Valid IngredientResource externalResource
) {
}
