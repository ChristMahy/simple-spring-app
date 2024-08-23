package cmahy.webapp.taco.shop.adapter.h2.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
//@ComponentScan(basePackages = { "cmahy.webapp.taco.shop.adapter.h2.repository" })
@EnableJpaRepositories(basePackages = { "cmahy.webapp.taco.shop.adapter.h2.repository" })
public class H2Configurer {
}
