package cmahy.springapp.kafka.publisher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
    scanBasePackages = {
        "cmahy.springapp.restresource.publisher",
        "cmahy.springapp.kafka.publisher"
    }
)
public class KafkaPublisherApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaPublisherApplication.class, args);
    }
}
