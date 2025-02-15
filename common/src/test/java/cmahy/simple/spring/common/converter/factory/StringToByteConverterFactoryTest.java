package cmahy.simple.spring.common.converter.factory;

import cmahy.simple.spring.common.converter.StringToByteConverter;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class StringToByteConverterFactoryTest {

    @Test
    void create() {
        assertDoesNotThrow(() -> {
            assertThat(StringToByteConverterFactory.create())
                .isNotNull()
                .isInstanceOf(StringToByteConverter.class);
        });
    }
}