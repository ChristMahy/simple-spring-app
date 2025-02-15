package cmahy.simple.spring.common.converter;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class StringToByteConverter {

    public byte[] convert(String source) {
        return Optional
            .ofNullable(source)
            .map(s -> s.getBytes(StandardCharsets.UTF_8))
            .orElse(null);
    }
}
