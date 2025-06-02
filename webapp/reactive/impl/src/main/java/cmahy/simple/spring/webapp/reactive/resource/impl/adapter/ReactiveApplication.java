package cmahy.simple.spring.webapp.reactive.resource.impl.adapter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
    "cmahy.simple.spring.webapp.reactive.resource.impl"
})
@EnableR2dbcRepositories(basePackages = {
    "cmahy.simple.spring.webapp.reactive.resource.impl.adapter.repository"
})
@EntityScan(basePackages = {
    "cmahy.simple.spring.webapp.reactive.resource.impl.domain"
})
public class ReactiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveApplication.class, args);
    }

}
