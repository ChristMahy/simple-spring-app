package cmahy.simple.spring.webapp.reactive.resource.impl.application.command;

import cmahy.simple.spring.common.annotation.Command;
import cmahy.simple.spring.webapp.reactive.resource.impl.application.mapper.input.TodoCreateInputMapper;
import cmahy.simple.spring.webapp.reactive.resource.impl.application.mapper.output.TodoOutputAppMapper;
import cmahy.simple.spring.webapp.reactive.resource.impl.application.repository.TodoRepository;
import cmahy.simple.spring.webapp.reactive.resource.impl.vo.input.TodoCreateInputVo;
import cmahy.simple.spring.webapp.reactive.resource.impl.vo.output.TodoOutputVo;
import jakarta.inject.Named;
import reactor.core.publisher.Mono;

@Command
@Named
public class CreateTodoCommand {


    private final TodoCreateInputMapper createInputMapper;
    private final TodoRepository todoRepository;
    private final TodoOutputAppMapper todoOutputAppMapper;

    public CreateTodoCommand(
        TodoCreateInputMapper createInputMapper,
        TodoRepository todoRepository,
        TodoOutputAppMapper todoOutputAppMapper
    ) {
        this.createInputMapper = createInputMapper;
        this.todoRepository = todoRepository;
        this.todoOutputAppMapper = todoOutputAppMapper;
    }

    public Mono<TodoOutputVo> execute(TodoCreateInputVo input) {
        return Mono.justOrEmpty(input)
            .map(createInputMapper::map)
            .flatMap(todoRepository::save)
            .map(todoOutputAppMapper::map);
    }
}
