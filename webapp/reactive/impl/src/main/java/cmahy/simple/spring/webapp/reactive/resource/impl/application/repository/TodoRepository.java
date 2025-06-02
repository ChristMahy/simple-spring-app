package cmahy.simple.spring.webapp.reactive.resource.impl.application.repository;

import cmahy.simple.spring.common.entity.page.EntityPageable;
import cmahy.simple.spring.webapp.reactive.resource.impl.domain.Todo;
import cmahy.simple.spring.webapp.reactive.resource.impl.domain.id.TodoId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface TodoRepository {

    Mono<Todo> findById(TodoId id);

    Flux<Todo> findAllByOrderByCreatedAtDesc(EntityPageable pageable);

    Mono<Todo> save(Todo todo);

}
