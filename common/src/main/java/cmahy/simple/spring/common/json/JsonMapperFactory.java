package cmahy.simple.spring.common.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public final class JsonMapperFactory {

    private JsonMapperFactory() {}

    public static ObjectMapper create() {
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.registerModules(new Jdk8Module(), new JavaTimeModule());

        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        objectMapper.enable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN);

        return objectMapper;
    }
}
