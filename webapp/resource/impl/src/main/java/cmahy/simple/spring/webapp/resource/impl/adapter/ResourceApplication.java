package cmahy.simple.spring.webapp.resource.impl.adapter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication(scanBasePackages = {
    "cmahy.simple.spring.webapp.resource.impl",
    "cmahy.simple.spring.webapp.taco.shop.kernel",
    "cmahy.simple.spring.webapp.user.kernel"
})
@ConfigurationPropertiesScan(basePackages = {
    "cmahy.simple.spring.webapp.resource.impl.adapter.config",
    "cmahy.simple.spring.webapp.resource.impl.adapter.security.config.properties",
    "cmahy.simple.spring.webapp.resource.impl.adapter.taco.shop.properties"
})
public class ResourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResourceApplication.class, args);
    }
}
