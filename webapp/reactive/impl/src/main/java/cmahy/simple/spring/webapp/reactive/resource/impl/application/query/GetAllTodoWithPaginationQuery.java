package cmahy.simple.spring.webapp.reactive.resource.impl.application.query;

import cmahy.simple.spring.common.annotation.Query;
import cmahy.simple.spring.common.entity.page.EntityPageable;
import cmahy.simple.spring.webapp.reactive.resource.impl.application.mapper.output.TodoOutputAppMapper;
import cmahy.simple.spring.webapp.reactive.resource.impl.application.repository.TodoRepository;
import cmahy.simple.spring.webapp.reactive.resource.impl.vo.output.TodoOutputVo;
import jakarta.inject.Named;
import reactor.core.publisher.Flux;

import java.util.Collection;

@Query
@Named
public class GetAllTodoWithPaginationQuery {

    private final TodoRepository todoRepository;
    private final TodoOutputAppMapper todoOutputMapper;

    public GetAllTodoWithPaginationQuery(
        TodoRepository todoRepository,
        TodoOutputAppMapper todoOutputMapper
    ) {
        this.todoRepository = todoRepository;
        this.todoOutputMapper = todoOutputMapper;
    }

    public Flux<TodoOutputVo> execute(EntityPageable pageable) {
        return todoRepository.findAllByOrderByCreatedAtDesc(pageable)
            .map(todoOutputMapper::map);
    }

}
