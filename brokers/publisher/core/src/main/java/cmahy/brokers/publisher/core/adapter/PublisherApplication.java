package cmahy.brokers.publisher.core.adapter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
    "cmahy.brokers.publisher"
})
public class PublisherApplication {
    public static void main(String[] args) {
        SpringApplication.run(PublisherApplication.class, args);
    }
}
