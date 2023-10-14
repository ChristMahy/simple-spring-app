package cmahy.brokers.consumer.core.adapter.config;

import org.springframework.boot.autoconfigure.jms.artemis.ArtemisAutoConfiguration;
import org.springframework.context.annotation.*;

@Profile("jms")
@Configuration
@ComponentScan("cmahy.brokers.consumer.jms")
@Import({ ArtemisAutoConfiguration.class })
public class JmsProfileConfigurer {
}
