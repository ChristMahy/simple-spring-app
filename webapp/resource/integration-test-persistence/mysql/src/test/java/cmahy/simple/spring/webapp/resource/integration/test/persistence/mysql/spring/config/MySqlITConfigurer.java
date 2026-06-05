package cmahy.simple.spring.webapp.resource.integration.test.persistence.mysql.spring.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@AutoConfiguration
@ComponentScan(basePackages = {
    "cmahy.simple.spring.webapp.resource.integration.test.persistence.api.command"
})
@EnableJpaRepositories(basePackages = {
    "cmahy.simple.spring.webapp.resource.integration.test.persistence.mysql.repository"
})
public class MySqlITConfigurer {

}
