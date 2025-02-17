package cmahy.simple.spring.brokers.consumer.core.adapter.config;

import org.springframework.boot.autoconfigure.jms.artemis.ArtemisAutoConfiguration;
import org.springframework.context.annotation.*;

@Profile("jms")
@Configuration
@ComponentScan("cmahy.simple.spring.brokers.consumer.jms")
@Import({ ArtemisAutoConfiguration.class })
public class JmsProfileConfigurer {
}
