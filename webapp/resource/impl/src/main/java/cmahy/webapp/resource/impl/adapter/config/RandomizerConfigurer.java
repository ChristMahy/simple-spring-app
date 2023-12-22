package cmahy.webapp.resource.impl.adapter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class RandomizerConfigurer {

    @Bean
    public Random randomizer() {
        return new Random();
    }
}
