package cmahy.simple.spring.webapp.reactive.resource.impl.adapter.mapper.input;

import cmahy.simple.spring.webapp.reactive.resource.api.todo.vo.input.TodoUpdateInputApiVo;
import cmahy.simple.spring.webapp.reactive.resource.impl.vo.input.TodoUpdateInputVo;
import jakarta.inject.Named;

@Named
public class TodoUpdateInputApiMapper {

    public TodoUpdateInputVo map(TodoUpdateInputApiVo input) {
        return new TodoUpdateInputVo(
            input.title(),
            input.description()
        );
    }
}
