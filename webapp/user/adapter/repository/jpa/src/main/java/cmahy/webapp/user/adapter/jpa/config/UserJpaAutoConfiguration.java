package cmahy.webapp.user.adapter.jpa.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@AutoConfiguration
@EnableJpaRepositories(basePackages = {"cmahy.webapp.user.adapter.jpa.repository"})
public class UserJpaAutoConfiguration {
}
