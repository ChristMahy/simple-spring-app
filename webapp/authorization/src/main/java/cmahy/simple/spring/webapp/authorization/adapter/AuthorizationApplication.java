package cmahy.simple.spring.webapp.authorization.adapter;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.persistence.autoconfigure.EntityScan;

@SpringBootApplication(scanBasePackages = {
    "cmahy.simple.spring.webapp.authorization.adapter",
    "cmahy.simple.spring.webapp.authorization.application"
})
@ConfigurationPropertiesScan(basePackages = {
    "cmahy.simple.spring.webapp.authorization.adapter.config.properties"
})
@EntityScan(basePackages = {
    "cmahy.simple.spring.webapp.authorization.domain"
})
public class AuthorizationApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorizationApplication.class, args);
    }
}
