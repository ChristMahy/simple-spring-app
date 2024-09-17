package cmahy.webapp.taco.shop.adapter.jpa.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@AutoConfiguration
@EnableJpaRepositories(basePackages = {"cmahy.webapp.taco.shop.adapter.jpa.repository"})
public class TacoShopJpaAutoConfiguration {
}
