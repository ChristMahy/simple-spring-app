package cmahy.simple.spring.webapp.reactive.resource.impl.application.mapper.output;

import cmahy.simple.spring.webapp.reactive.resource.impl.domain.Todo;
import cmahy.simple.spring.webapp.reactive.resource.impl.domain.id.TodoId;
import cmahy.simple.spring.webapp.reactive.resource.impl.vo.output.TodoOutputVo;
import jakarta.inject.Named;

@Named
public class TodoOutputAppMapper {

    public TodoOutputVo map(Todo todo) {
        return new TodoOutputVo(
            new TodoId(todo.id()),
            todo.title(),
            todo.description(),
            todo.createdAt()
        );
    }
}
