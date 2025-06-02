package cmahy.simple.spring.webapp.reactive.resource.api.todo;

import cmahy.simple.spring.webapp.reactive.resource.api.todo.vo.id.TodoApiId;
import cmahy.simple.spring.webapp.reactive.resource.api.todo.vo.input.TodoCreateInputApiVo;
import cmahy.simple.spring.webapp.reactive.resource.api.todo.vo.input.TodoUpdateInputApiVo;
import cmahy.simple.spring.webapp.reactive.resource.api.todo.vo.output.TodoOutputApiVo;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Validated
@RequestMapping(TodoApi.BASE_URL)
public interface TodoApi {

    String BASE_URL = "/todo";

    @GetMapping(produces = {MediaType.APPLICATION_NDJSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    Flux<TodoOutputApiVo> all(
        @RequestParam(name = "page-number") Integer pageNumber,
        @RequestParam(name = "page-size") Integer pageSize
    );

    @PostMapping(produces = {MediaType.APPLICATION_NDJSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    Mono<TodoOutputApiVo> create(
        @RequestBody @Valid Mono<TodoCreateInputApiVo> createTodo
    );

    @PutMapping(
        path = "/{id:[a-zA-Z0-9-]+}",
        produces = {MediaType.APPLICATION_NDJSON_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    Mono<TodoOutputApiVo> partialUpdate(
        @PathVariable TodoApiId id,
        @RequestBody Mono<TodoUpdateInputApiVo> updateTodo
    );
}
