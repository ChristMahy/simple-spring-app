package cmahy.springapp.reactive.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class RandomConfigurer {

    @Bean
    public Random randomizer() {
        return new Random();
    }
}
