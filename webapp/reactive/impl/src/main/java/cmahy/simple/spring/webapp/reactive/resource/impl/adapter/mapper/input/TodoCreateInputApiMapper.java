package cmahy.simple.spring.webapp.reactive.resource.impl.adapter.mapper.input;

import cmahy.simple.spring.webapp.reactive.resource.api.todo.vo.input.TodoCreateInputApiVo;
import cmahy.simple.spring.webapp.reactive.resource.impl.vo.input.TodoCreateInputVo;
import jakarta.inject.Named;

@Named
public class TodoCreateInputApiMapper {

    public TodoCreateInputVo map(TodoCreateInputApiVo input) {
        return new TodoCreateInputVo(
            input.title(),
            input.description()
        );
    }
}
