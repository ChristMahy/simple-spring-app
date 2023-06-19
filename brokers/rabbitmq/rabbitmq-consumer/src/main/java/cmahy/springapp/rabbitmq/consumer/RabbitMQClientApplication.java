package cmahy.springapp.rabbitmq.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
    scanBasePackages = {
        "cmahy.springapp.restresource.consumer",
        "cmahy.springapp.rabbitmq.consumer"
    }
)
public class RabbitMQClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(RabbitMQClientApplication.class, args);
    }
}
