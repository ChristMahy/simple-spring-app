package cmahy.springapp.rabbitmq.publisher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
    scanBasePackages = {
        "cmahy.springapp.restresource.publisher",
        "cmahy.springapp.rabbitmq.publisher"
    }
)
public class RabbitMQResourceApplication {
    public static void main(String[] args) {
        SpringApplication.run(RabbitMQResourceApplication.class, args);
    }
}
