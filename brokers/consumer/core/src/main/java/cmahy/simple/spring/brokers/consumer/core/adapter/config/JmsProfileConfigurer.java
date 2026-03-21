package cmahy.simple.spring.brokers.consumer.core.adapter.config;

import org.springframework.context.annotation.*;

@Profile("jms")
@Configuration
@ComponentScan("cmahy.simple.spring.brokers.consumer.jms")
public class JmsProfileConfigurer {
}
