package cmahy.simple.spring.webapp.resource.integration.test.persistence.cassandra.spring.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@AutoConfiguration
@ComponentScan(basePackages = {
    "cmahy.simple.spring.webapp.resource.integration.test.persistence.api.command",
    "cmahy.simple.spring.webapp.resource.integration.test.persistence.cassandra.repository.impl"
})
@EnableCassandraRepositories(basePackages = {
    "cmahy.simple.spring.webapp.resource.integration.test.persistence.cassandra.repository.cassandra"
})
public class CassandraITKeyspaceSnapshotConfigurer {
}
