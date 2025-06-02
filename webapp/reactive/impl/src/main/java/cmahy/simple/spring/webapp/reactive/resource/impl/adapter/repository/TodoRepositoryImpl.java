package cmahy.simple.spring.webapp.reactive.resource.impl.adapter.repository;

import cmahy.simple.spring.common.entity.page.EntityPageable;
import cmahy.simple.spring.webapp.reactive.resource.impl.application.repository.TodoRepository;
import cmahy.simple.spring.webapp.reactive.resource.impl.domain.Todo;
import cmahy.simple.spring.webapp.reactive.resource.impl.domain.id.TodoId;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;


@Component
public class TodoRepositoryImpl implements TodoRepository {

    private final TodoReactiveRepositoryImpl todoReactiveRepository;

    public TodoRepositoryImpl(TodoReactiveRepositoryImpl todoReactiveRepository) {
        this.todoReactiveRepository = todoReactiveRepository;
    }

    @Override
    public Mono<Todo> findById(TodoId id) {
        return todoReactiveRepository
            .findById(id.value());
    }

    @Override
    public Flux<Todo> findAllByOrderByCreatedAtDesc(EntityPageable pageable) {
        Long limit = Optional.ofNullable(pageable)
            .map(EntityPageable::pageSize)
            .map(Integer::longValue)
            .orElse(10L);

        Long offset = Optional.ofNullable(pageable)
            .map(EntityPageable::pageNumber)
            .map(pn -> pn * limit)
            .orElse(0L);

        return todoReactiveRepository
            .findAllByOrderByCreatedAtDesc(limit, offset)
            .log();
    }

    @Override
    public Mono<Todo> save(Todo todo) {
        return todoReactiveRepository
            .save(todo);
    }

}
