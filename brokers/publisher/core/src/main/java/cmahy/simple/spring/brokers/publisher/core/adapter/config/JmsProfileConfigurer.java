package cmahy.simple.spring.brokers.publisher.core.adapter.config;

import org.springframework.boot.autoconfigure.jms.artemis.ArtemisAutoConfiguration;
import org.springframework.context.annotation.*;

@Profile("jms")
@Configuration
@ComponentScan("cmahy.brokers.publisher.jms")
@Import({ ArtemisAutoConfiguration.class })
public class JmsProfileConfigurer {
}
