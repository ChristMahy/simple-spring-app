package cmahy.simple.spring.webapp.reactive.resource.impl.adapter.generator;

import cmahy.simple.spring.webapp.reactive.resource.impl.application.command.CreateTodoCommand;
import cmahy.simple.spring.webapp.reactive.resource.impl.vo.input.TodoCreateInputVo;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.stream.IntStream;

@Component
public class TodoGenerator implements ApplicationRunner {

    private final CreateTodoCommand createTodoCommand;

    public TodoGenerator(
        CreateTodoCommand createTodoCommand
    ) {
        this.createTodoCommand = createTodoCommand;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Flux.fromStream(
                IntStream.rangeClosed(0, 100000).mapToObj(cpt -> new TodoCreateInputVo(
                    "First App with reactive " + cpt,
                    "Implement my first spring app as reactive " + cpt
                ))
            )
            .flatMap(createTodoCommand::execute)
            .subscribeOn(Schedulers.parallel())
            .subscribe();
    }
}
