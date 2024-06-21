package cmahy.webapp.resource.impl.adapter.config;

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Configuration
public class PropertiesConfigurer {

    @Bean
    @ConfigurationPropertiesBinding
    public Converter<String, byte[]> stringToBytesConverter() {
        return new Converter<String, byte[]>() {
            @Override
            public byte[] convert(String source) {
                return Optional
                    .ofNullable(source)
                    .map(s -> s.getBytes(StandardCharsets.UTF_8))
                    .orElse(null);
            }
        };
    }
}
