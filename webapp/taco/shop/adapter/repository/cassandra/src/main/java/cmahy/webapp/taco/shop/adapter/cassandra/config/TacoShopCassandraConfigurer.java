package cmahy.webapp.taco.shop.adapter.cassandra.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@EnableCassandraRepositories(basePackages = { "cmahy.webapp.taco.shop.adapter.cassandra.repository" })
public class TacoShopCassandraConfigurer {
}
