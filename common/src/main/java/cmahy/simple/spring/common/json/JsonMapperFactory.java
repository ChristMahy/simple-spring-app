package cmahy.simple.spring.common.json;

import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

import static tools.jackson.core.StreamWriteFeature.WRITE_BIGDECIMAL_AS_PLAIN;
import static tools.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static tools.jackson.databind.cfg.DateTimeFeature.WRITE_DATES_AS_TIMESTAMPS;

public final class JsonMapperFactory {

    private JsonMapperFactory() {}

    public static ObjectMapper create() {


        return JsonMapper.builder()
            .addModule(new UUIDModule())
            .disable(WRITE_DATES_AS_TIMESTAMPS)
            .disable(FAIL_ON_UNKNOWN_PROPERTIES)
            .enable(WRITE_BIGDECIMAL_AS_PLAIN)
            .build();

    }

}
