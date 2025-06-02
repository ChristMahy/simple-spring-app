package cmahy.simple.spring.webapp.taco.shop.adapter.webclient.config.properties.ingredient;

import jakarta.validation.Valid;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "application.taco.ingredients")
public record IngredientProperties(
    @Valid IngredientExternalResource externalResource
) {
}
