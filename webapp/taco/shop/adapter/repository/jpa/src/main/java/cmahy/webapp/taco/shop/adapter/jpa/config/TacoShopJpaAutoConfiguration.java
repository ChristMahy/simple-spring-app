package cmahy.webapp.taco.shop.adapter.jpa.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@AutoConfiguration
@EntityScan(basePackages = "cmahy.webapp.taco.shop.adapter.jpa.entity.domain")
@EnableJpaRepositories(basePackages = {"cmahy.webapp.taco.shop.adapter.jpa.repository"})
@ComponentScan(basePackages = "cmahy.webapp.taco.shop.adapter.jpa.entity.builder")
public class TacoShopJpaAutoConfiguration {
}
