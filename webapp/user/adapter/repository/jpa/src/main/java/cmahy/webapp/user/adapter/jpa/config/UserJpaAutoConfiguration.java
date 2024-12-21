package cmahy.webapp.user.adapter.jpa.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@AutoConfiguration
@EntityScan(basePackages = {"cmahy.webapp.user.adapter.jpa.entity"})
@EnableJpaRepositories(basePackages = {"cmahy.webapp.user.adapter.jpa.repository"})
@ComponentScan(basePackages = {"cmahy.webapp.user.adapter.jpa.entity.builder"})
public class UserJpaAutoConfiguration {
}
