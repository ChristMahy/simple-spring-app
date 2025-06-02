package cmahy.simple.spring.webapp.reactive.resource.impl.application.mapper.input;

import cmahy.simple.spring.webapp.reactive.resource.impl.domain.Todo;
import cmahy.simple.spring.webapp.reactive.resource.impl.vo.input.TodoUpdateInputVo;
import jakarta.inject.Named;
import org.apache.commons.lang3.StringUtils;

@Named
public class TodoUpdateInputMapper {

    public Todo map(Todo todo, TodoUpdateInputVo input) {
        Todo updated = todo;

        if (input.title().isPresent()) {
            updated = replaceTitle(updated, input.title().get());
        }

        if (input.description().isPresent()) {
            updated = replaceDescription(updated, input.description().get());
        }

        return updated;
    }

    private Todo replaceTitle(Todo todo, String title) {
        return new Todo(
            todo.id(),
            StringUtils.defaultIfEmpty(title, null),
            todo.description(),
            todo.createdAt()
        );
    }

    private Todo replaceDescription(Todo todo, String description) {
        return new Todo(
            todo.id(),
            todo.title(),
            StringUtils.defaultIfEmpty(description, null),
            todo.createdAt()
        );
    }
}
