package cmahy.springapp.jms.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = {
                "cmahy.springapp.restresource.consumer",
                "cmahy.springapp.jms.consumer"
        }
)
public class JmsClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(JmsClientApplication.class, args);
    }
}
