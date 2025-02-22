package cmahy.simple.spring.webapp.resource.impl.adapter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

@Configuration
public class DateConfigurer {

    @Bean
    public DateTimeFormatter yearMonthDayHoursMinutesSeconds() {
        return DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    }
}
