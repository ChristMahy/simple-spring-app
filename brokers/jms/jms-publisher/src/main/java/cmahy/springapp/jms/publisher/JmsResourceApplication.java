package cmahy.springapp.jms.publisher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = {
                "cmahy.springapp.restresource.publisher",
                "cmahy.springapp.jms.publisher"
        }
)
public class JmsResourceApplication {
    public static void main(String[] args) {
        SpringApplication.run(JmsResourceApplication.class, args);
    }
}
