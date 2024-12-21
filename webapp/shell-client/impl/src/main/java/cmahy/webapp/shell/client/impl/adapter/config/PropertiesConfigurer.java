package cmahy.webapp.shell.client.impl.adapter.config;

import cmahy.common.converter.StringToByteConverter;
import cmahy.common.converter.factory.StringToByteConverterFactory;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

@Configuration
public class PropertiesConfigurer {

    @Bean
    public StringToByteConverter stringToBytesConverterImpl() {
        return StringToByteConverterFactory.create();
    }

    @Bean
    @ConfigurationPropertiesBinding
    public Converter<String, byte[]> stringToBytesConverter(StringToByteConverter converter) {
        return new Converter<>() {
            @Override
            public byte[] convert(String source) {
                return converter.convert(source);
            }
        };
    }
}
