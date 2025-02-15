package cmahy.simple.spring.common.json;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class JsonMapperFactoryTest {

    @Test
    void create() {
        assertDoesNotThrow(() -> {
            assertThat(JsonMapperFactory.create()).isNotNull();
        });
    }
}