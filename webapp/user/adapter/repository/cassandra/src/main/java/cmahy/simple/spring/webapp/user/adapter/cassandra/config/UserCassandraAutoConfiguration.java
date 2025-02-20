package cmahy.simple.spring.webapp.user.adapter.cassandra.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@AutoConfiguration
@EntityScan(basePackages = {"cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain"})
@EnableCassandraRepositories(basePackages = {"cmahy.simple.spring.webapp.user.adapter.cassandra.repository.cassandra"})
@ComponentScan(
    basePackages = {
        "cmahy.simple.spring.webapp.user.adapter.cassandra.repository.impl",
        "cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy",
        "cmahy.simple.spring.webapp.user.adapter.cassandra.entity.loader",
        "cmahy.simple.spring.webapp.user.adapter.cassandra.entity.builder"
    },
    basePackageClasses = {UserCodecConfigurer.class}
)
public class UserCassandraAutoConfiguration {
}
