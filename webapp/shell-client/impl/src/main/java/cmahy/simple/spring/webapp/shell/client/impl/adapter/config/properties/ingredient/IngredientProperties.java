package cmahy.simple.spring.webapp.shell.client.impl.adapter.config.properties.ingredient;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "application.taco.ingredients")
public record IngredientProperties(
    IngredientResource externalResource
) {
}
