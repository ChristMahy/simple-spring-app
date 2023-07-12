package cmahy.springapp.reactive.domain;

import org.springframework.data.annotation.Id;

public record Todo(
    @Id Long id,
    String title,
    String description
) {
    public Todo(String title, String description) {
        this(null, title, description);
    }
}
