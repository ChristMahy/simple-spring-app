package cmahy.webapp.resource.impl.adapter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication(scanBasePackages = {
    "cmahy.webapp.resource.impl",
    "cmahy.webapp.taco.shop.kernel",
    "cmahy.webapp.user.kernel"
})
@ConfigurationPropertiesScan(basePackages = {
    "cmahy.webapp.resource.impl.adapter.config",
    "cmahy.webapp.resource.impl.adapter.security.config.properties",
    "cmahy.webapp.resource.impl.adapter.taco.shop.properties"
})
public class ResourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResourceApplication.class, args);
    }
}
