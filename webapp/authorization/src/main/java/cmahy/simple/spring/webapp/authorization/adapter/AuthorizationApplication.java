package cmahy.simple.spring.webapp.authorization.adapter;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = {
    "cmahy.simple.spring.webapp.authorization.adapter",
    "cmahy.simple.spring.webapp.authorization.application"
})
@EntityScan(basePackages = {
    "cmahy.simple.spring.webapp.authorization.domain"
})
public class AuthorizationApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorizationApplication.class, args);
    }
}
