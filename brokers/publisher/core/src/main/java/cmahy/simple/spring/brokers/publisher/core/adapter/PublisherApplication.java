package cmahy.simple.spring.brokers.publisher.core.adapter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisAutoConfiguration;

@SpringBootApplication(scanBasePackages = { "cmahy.brokers.publisher.core" }, exclude = { ArtemisAutoConfiguration.class, RabbitAutoConfiguration.class })
public class PublisherApplication {
    public static void main(String[] args) {
        SpringApplication.run(PublisherApplication.class, args);
    }
}
