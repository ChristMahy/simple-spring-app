package cmahy.simple.spring.webapp.resource.integration.test.persistence.h2.spring.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@AutoConfiguration
@ComponentScan(basePackages = {
    "cmahy.simple.spring.webapp.resource.integration.test.persistence.application.command"
})
@EnableJpaRepositories(basePackages = {
    "cmahy.simple.spring.webapp.resource.integration.test.persistence.h2.repository"
})
public class H2ITConfigurer {
}
