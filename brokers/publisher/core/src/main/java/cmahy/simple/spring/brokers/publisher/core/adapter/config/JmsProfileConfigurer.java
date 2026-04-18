package cmahy.simple.spring.brokers.publisher.core.adapter.config;

import org.springframework.context.annotation.*;

@Profile("jms")
@Configuration
@ComponentScan("cmahy.simple.spring.brokers.publisher.jms")
public class JmsProfileConfigurer {
}
