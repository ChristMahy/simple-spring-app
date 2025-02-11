package cmahy.webapp.taco.shop.adapter.cassandra.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@AutoConfiguration
@EntityScan(basePackages = {"cmahy.webapp.taco.shop.adapter.cassandra.entity.domain"})
@EnableCassandraRepositories(basePackages = { "cmahy.webapp.taco.shop.adapter.cassandra.repository.cassandra" })
@ComponentScan(
    basePackages = {
        "cmahy.webapp.taco.shop.adapter.cassandra.repository.impl",
        "cmahy.webapp.taco.shop.adapter.cassandra.entity.proxy",
        "cmahy.webapp.taco.shop.adapter.cassandra.entity.loader",
        "cmahy.webapp.taco.shop.adapter.cassandra.entity.builder"
    },
    basePackageClasses = {TacoShopCodecConfigurer.class}
)
public class TacoShopCassandraConfigurer {
}
