package cmahy.simple.spring.webapp.reactive.resource.impl.adapter.api;

import cmahy.simple.spring.common.entity.page.*;
import cmahy.simple.spring.webapp.reactive.resource.api.todo.TodoApi;
import cmahy.simple.spring.webapp.reactive.resource.api.todo.vo.id.TodoApiId;
import cmahy.simple.spring.webapp.reactive.resource.api.todo.vo.input.TodoCreateInputApiVo;
import cmahy.simple.spring.webapp.reactive.resource.api.todo.vo.input.TodoUpdateInputApiVo;
import cmahy.simple.spring.webapp.reactive.resource.api.todo.vo.output.TodoOutputApiVo;
import cmahy.simple.spring.webapp.reactive.resource.impl.adapter.mapper.input.TodoCreateInputApiMapper;
import cmahy.simple.spring.webapp.reactive.resource.impl.adapter.mapper.input.TodoUpdateInputApiMapper;
import cmahy.simple.spring.webapp.reactive.resource.impl.adapter.mapper.output.TodoOutputApiMapper;
import cmahy.simple.spring.webapp.reactive.resource.impl.application.command.CreateTodoCommand;
import cmahy.simple.spring.webapp.reactive.resource.impl.application.command.UpdateTodoCommand;
import cmahy.simple.spring.webapp.reactive.resource.impl.application.query.GetAllTodoWithPaginationQuery;
import cmahy.simple.spring.webapp.reactive.resource.impl.domain.id.TodoId;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController
public class TodoApiImpl implements TodoApi {

    private final GetAllTodoWithPaginationQuery query;
    private final TodoOutputApiMapper outputApiMapper;
    private final TodoCreateInputApiMapper todoCreateInputMapper;
    private final TodoUpdateInputApiMapper todoUpdateInputMapper;
    private final CreateTodoCommand createTodoCommand;
    private final UpdateTodoCommand updateTodoCommand;

    public TodoApiImpl(
        GetAllTodoWithPaginationQuery query,
        TodoOutputApiMapper outputApiMapper,
        TodoCreateInputApiMapper todoCreateInputMapper,
        TodoUpdateInputApiMapper todoUpdateInputMapper,
        CreateTodoCommand createTodoCommand,
        UpdateTodoCommand updateTodoCommand
    ) {
        this.query = query;
        this.outputApiMapper = outputApiMapper;
        this.todoCreateInputMapper = todoCreateInputMapper;
        this.todoUpdateInputMapper = todoUpdateInputMapper;
        this.createTodoCommand = createTodoCommand;
        this.updateTodoCommand = updateTodoCommand;
    }

    @Override
    public Flux<TodoOutputApiVo> all(Integer pageNumber, Integer pageSize) {
        return query
            .execute(
                EntityPageableBuilder
                    .<DefaultEntityPageableImpl>instance(10)
                    .withPageNumber(pageNumber)
                    .withPageSize(pageSize)
                    .build(DefaultEntityPageableImpl.class)
            )
            .map(outputApiMapper::map);
    }

    @Override
    public Mono<TodoOutputApiVo> create(Mono<TodoCreateInputApiVo> createTodo) {
        return createTodo
            .map(todoCreateInputMapper::map)
            .flatMap(createTodoCommand::execute)
            .map(outputApiMapper::map);
    }

    @Override
    public Mono<TodoOutputApiVo> partialUpdate(
        TodoApiId id,
        Mono<TodoUpdateInputApiVo> updateTodo
    ) {
        return updateTodo
            .log()
            .map(todoUpdateInputMapper::map)
            .flatMap(inputVo ->
                updateTodoCommand.execute(new TodoId(id.value()), inputVo)
            )
            .map(outputApiMapper::map);
    }
}
