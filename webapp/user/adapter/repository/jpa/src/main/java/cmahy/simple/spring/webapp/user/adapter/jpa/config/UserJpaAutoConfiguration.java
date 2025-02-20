package cmahy.simple.spring.webapp.user.adapter.jpa.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@AutoConfiguration
@EntityScan(basePackages = {"cmahy.simple.spring.webapp.user.adapter.jpa.entity.domain"})
@EnableJpaRepositories(basePackages = {"cmahy.simple.spring.webapp.user.adapter.jpa.repository"})
@ComponentScan(
    basePackages = {
        "cmahy.simple.spring.webapp.user.adapter.jpa.entity.builder",
        "cmahy.simple.spring.webapp.user.adapter.jpa.entity.id.factory"
    }
)
public class UserJpaAutoConfiguration {
}
