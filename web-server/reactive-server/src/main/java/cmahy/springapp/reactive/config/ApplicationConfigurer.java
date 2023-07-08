package cmahy.springapp.reactive.config;

import cmahy.springapp.reactive.domain.Todo;
import cmahy.springapp.reactive.service.TodoService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfigurer {

    @Bean
    public ApplicationRunner populateSomeTodos(TodoService todoService) {
        return args -> {
            Todo todo;

            long cpt = 0L;

            while (cpt++ < 1000) {
                todo = new Todo();

                todo.setId(cpt);
                todo.setTitle("First App with reactive " + cpt);
                todo.setDescription("Implement my first spring app as reactive " + cpt);

                todoService.save(todo);
            }
        };
    }
}
