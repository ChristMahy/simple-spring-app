package cmahy.webapp.shell.client.impl.adapter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Configuration
public class DateFormatterConfigurer {

    @Bean
    public DateTimeFormatter belgiumSimpleDateFormatter() {
        // TODO: parameterize ZoneId ! And, why not format date, with default to this ????
        return DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
            .withZone(ZoneId.of("Europe/Brussels"));
    }
}
