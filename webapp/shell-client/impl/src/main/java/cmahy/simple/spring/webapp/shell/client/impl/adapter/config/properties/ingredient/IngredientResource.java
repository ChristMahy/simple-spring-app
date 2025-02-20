package cmahy.simple.spring.webapp.shell.client.impl.adapter.config.properties.ingredient;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.util.Optional;

public record IngredientResource(
    @NotNull(message = "Must be not null")
    @NotBlank(message = "Must be not blank")
    String baseUrl,
    Optional<Integer> pageSize,
    SslOption ssl
) {
    @ConstructorBinding
    public IngredientResource(
        @NotNull(message = "Must be not null")
        @NotBlank(message = "Must be not blank")
        String baseUrl,
        Integer pageSize,
        SslOption ssl
    ) {
        this(
            baseUrl,
            Optional.ofNullable(pageSize),
            ssl
        );
    }
}
