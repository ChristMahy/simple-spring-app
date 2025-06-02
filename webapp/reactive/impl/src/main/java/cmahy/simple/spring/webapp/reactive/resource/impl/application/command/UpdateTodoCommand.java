package cmahy.simple.spring.webapp.reactive.resource.impl.application.command;

import cmahy.simple.spring.common.annotation.Command;
import cmahy.simple.spring.webapp.reactive.resource.impl.application.mapper.input.TodoUpdateInputMapper;
import cmahy.simple.spring.webapp.reactive.resource.impl.application.mapper.output.TodoOutputAppMapper;
import cmahy.simple.spring.webapp.reactive.resource.impl.application.repository.TodoRepository;
import cmahy.simple.spring.webapp.reactive.resource.impl.domain.id.TodoId;
import cmahy.simple.spring.webapp.reactive.resource.impl.exception.todo.TodoNotFoundException;
import cmahy.simple.spring.webapp.reactive.resource.impl.vo.input.TodoUpdateInputVo;
import cmahy.simple.spring.webapp.reactive.resource.impl.vo.output.TodoOutputVo;
import jakarta.inject.Named;
import reactor.core.publisher.Mono;

@Command
@Named
public class UpdateTodoCommand {

    private final TodoRepository todoRepository;
    private final TodoUpdateInputMapper updateInputMapper;
    private final TodoOutputAppMapper outputAppMapper;

    public UpdateTodoCommand(
        TodoRepository todoRepository,
        TodoUpdateInputMapper updateInputMapper,
        TodoOutputAppMapper outputAppMapper
    ) {
        this.todoRepository = todoRepository;
        this.updateInputMapper = updateInputMapper;
        this.outputAppMapper = outputAppMapper;
    }

    public Mono<TodoOutputVo> execute(TodoId id, TodoUpdateInputVo todoUpdateInputVo) {
        return todoRepository.findById(id)
            .switchIfEmpty(Mono.error(
                new TodoNotFoundException(String.format(
                    "<Todo> with ID <%s> not found",
                    id.value()
                ))
            ))
            .map(todo -> updateInputMapper.map(todo, todoUpdateInputVo))
            .flatMap(todoRepository::save)
            .map(outputAppMapper::map);
    }
}
