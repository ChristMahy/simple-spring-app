package cmahy.simple.spring.webapp.reactive.resource.api.todo.vo.input;

import jakarta.validation.constraints.*;

public record TodoCreateInputApiVo(
    @NotBlank String title,
    @NotBlank String description
) {
}
