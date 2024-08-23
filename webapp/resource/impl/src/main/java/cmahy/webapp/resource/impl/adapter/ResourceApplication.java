package cmahy.webapp.resource.impl.adapter;

import cmahy.webapp.taco.shop.adapter.h2.EnableH2Adapter;
import cmahy.webapp.taco.shop.adapter.webclient.EnableWebClientAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = { "cmahy.webapp.resource.impl", "cmahy.webapp.taco.shop.kernel", "cmahy.webapp.user.kernel" })
@EntityScan(basePackages = { "cmahy.webapp.user.kernel.domain", "cmahy.webapp.taco.shop.kernel.domain" })
@ConfigurationPropertiesScan(
    basePackages = {
        "cmahy.webapp.resource.impl.adapter.config",
        "cmahy.webapp.resource.impl.adapter.security.config.properties",
        "cmahy.webapp.resource.impl.adapter.taco.shop.properties"
    }
)
@EnableJpaRepositories(basePackages = { "cmahy.webapp.resource.impl", "cmahy.webapp.taco.shop.kernel", "cmahy.webapp.user.kernel" })
@EnableH2Adapter
@EnableWebClientAdapter
public class ResourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResourceApplication.class, args);
    }
}
