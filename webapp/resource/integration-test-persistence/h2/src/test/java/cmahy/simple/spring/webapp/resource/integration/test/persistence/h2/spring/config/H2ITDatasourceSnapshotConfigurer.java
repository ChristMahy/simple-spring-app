package cmahy.simple.spring.webapp.resource.integration.test.persistence.h2.spring.config;

//import cmahy.simple.spring.webapp.resource.integration.test.persistence.h2.H2ITDatasourceSnapshot;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@AutoConfiguration
//@Import(H2ITDatasourceSnapshot.class)
@ComponentScan(basePackages = {
    "cmahy.simple.spring.webapp.resource.integration.test.persistence.api.command"
})
@EnableJpaRepositories(basePackages = {
    "cmahy.simple.spring.webapp.resource.integration.test.persistence.h2.repository"
})
public class H2ITDatasourceSnapshotConfigurer {
}
