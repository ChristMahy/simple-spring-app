package cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@AutoConfiguration
@EntityScan(basePackages = {"cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.domain"})
@EnableCassandraRepositories(basePackages = { "cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.repository.cassandra" })
@ComponentScan(
    basePackages = {
        "cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.repository.impl",
        "cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.proxy",
        "cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.loader",
        "cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.builder"
    },
    basePackageClasses = {TacoShopCodecConfigurer.class}
)
public class TacoShopCassandraConfigurer {
}
