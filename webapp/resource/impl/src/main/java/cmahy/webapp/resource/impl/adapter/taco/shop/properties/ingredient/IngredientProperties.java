package cmahy.webapp.resource.impl.adapter.taco.shop.properties.ingredient;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "application.taco.ingredients")
public record IngredientProperties(
    IngredientExternalResource externalResource
) {
}
