package cmahy.webapp.resource.impl.adapter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestTemplateConfigurer {

    @Bean
    public RestTemplate commonRestTemplate(
        RestTemplateBuilder restTemplateBuilder,
        @Value("${application.rest-template.common.connect-timeout}")
        Duration connectTimeout,
        @Value("${application.rest-template.common.read-timeout}")
        Duration readTimeout
    ) {
        return restTemplateBuilder
            .setConnectTimeout(connectTimeout)
            .setReadTimeout(readTimeout)
            .build();
    }
}
