package cmahy.simple.spring.webapp.reactive.resource.impl.adapter.repository;

import cmahy.simple.spring.webapp.reactive.resource.impl.domain.Todo;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface TodoReactiveRepositoryImpl extends ReactiveCrudRepository<Todo, UUID> {

    @Query("select t.* from todo as t order by t.created_at desc limit ?1 offset ?2")
    Flux<Todo> findAllByOrderByCreatedAtDesc(Long limit, Long offset);

    Flux<Todo> findAllByOrderByCreatedAtDesc();

}
