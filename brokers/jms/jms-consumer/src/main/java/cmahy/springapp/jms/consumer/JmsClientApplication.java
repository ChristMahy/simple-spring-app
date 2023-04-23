package cmahy.springapp.jms.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JmsClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(JmsClientApplication.class, args);
    }
}
