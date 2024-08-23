package cmahy.webapp.shell.client.impl.adapter.config.properties;

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Component
@ConfigurationPropertiesBinding
public class StringToByteConverterProperties implements Converter<String, byte[]> {

    @Override
    public byte[] convert(String source) {
        return Optional
            .ofNullable(source)
            .map(s -> s.getBytes(StandardCharsets.UTF_8))
            .orElse(null);
    }
}
