package cmahy.simple.spring.webapp.reactive.resource.impl.application.mapper.input;

import cmahy.simple.spring.webapp.reactive.resource.impl.domain.Todo;
import cmahy.simple.spring.webapp.reactive.resource.impl.vo.input.TodoCreateInputVo;
import jakarta.inject.Named;

@Named
public class TodoCreateInputMapper {

    public Todo map(TodoCreateInputVo input) {
        return new Todo(
            input.title(),
            input.description()
        );
//        return Mono.justOrEmpty(input)
//            .map(in -> new Todo(
//                in.title(),
//                in.description()
//            ));
    }
}
