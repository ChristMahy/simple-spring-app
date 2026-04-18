package cmahy.simple.spring.brokers.publisher.core.adapter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
    scanBasePackages = { "cmahy.simple.spring.brokers.publisher.core" },
    excludeName = {
        "org.springframework.boot.amqp.autoconfigure.RabbitAutoConfiguration",
        "org.springframework.boot.kafka.autoconfigure.KafkaAutoConfiguration",
        "org.springframework.boot.artemis.autoconfigure.ArtemisAutoConfiguration"
    }
)
public class PublisherApplication {
    public static void main(String[] args) {
        SpringApplication.run(PublisherApplication.class, args);
    }
}
