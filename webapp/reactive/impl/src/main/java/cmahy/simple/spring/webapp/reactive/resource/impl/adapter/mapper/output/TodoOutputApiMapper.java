package cmahy.simple.spring.webapp.reactive.resource.impl.adapter.mapper.output;

import cmahy.simple.spring.webapp.reactive.resource.api.todo.vo.id.TodoApiId;
import cmahy.simple.spring.webapp.reactive.resource.api.todo.vo.output.TodoOutputApiVo;
import cmahy.simple.spring.webapp.reactive.resource.impl.vo.output.TodoOutputVo;
import jakarta.inject.Named;

@Named
public class TodoOutputApiMapper {

    public TodoOutputApiVo map(TodoOutputVo outputVo) {
        return new TodoOutputApiVo(
            new TodoApiId(outputVo.id().value()),
            outputVo.title(),
            outputVo.description(),
            outputVo.createdAt()
        );
    }
}
