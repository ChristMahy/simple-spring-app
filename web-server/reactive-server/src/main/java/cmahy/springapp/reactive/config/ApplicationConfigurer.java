package cmahy.springapp.reactive.config;

import cmahy.springapp.reactive.domain.Todo;
import cmahy.springapp.reactive.service.TodoService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.stream.IntStream;
import java.util.stream.Stream;

@Configuration
@EnableWebFlux
public class ApplicationConfigurer implements ApplicationRunner {

    private final TodoService todoService;

    public ApplicationConfigurer(TodoService todoService) {
        this.todoService = todoService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Flux.fromStream(
            IntStream.rangeClosed(3001, 4000).mapToObj(cpt -> new Todo(
                null,
                "First App with reactive " + cpt,
                "Implement my first spring app as reactive " + cpt
            ))
        )
            .flatMap(todoService::save)
            .subscribeOn(Schedulers.parallel())
            .subscribe();
    }
}
