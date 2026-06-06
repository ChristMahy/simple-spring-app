package cmahy.simple.spring.webapp.resource.impl.adapter.config;

import org.apache.commons.lang3.Strings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StringsConfigurator {

    @Bean
    public Strings ciStrings() {
        return Strings.CI;
    }

    @Bean
    public Strings csStrings() {
        return Strings.CS;
    }

}
