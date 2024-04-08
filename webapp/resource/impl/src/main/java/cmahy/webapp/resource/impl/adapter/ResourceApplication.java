package cmahy.webapp.resource.impl.adapter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = { "cmahy.webapp.resource.impl" })
@EntityScan(basePackages = { "cmahy.webapp.resource.impl.adapter.user.domain", "cmahy.webapp.resource.impl.domain" })
public class ResourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResourceApplication.class, args);
    }
}
