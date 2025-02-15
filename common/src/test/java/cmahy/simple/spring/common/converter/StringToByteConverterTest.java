package cmahy.simple.spring.common.converter;

import cmahy.simple.spring.common.helper.Generator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
class StringToByteConverterTest {

    @InjectMocks
    private StringToByteConverter stringToByteConverter;

    @Test
    void convert() {
        assertDoesNotThrow(() -> {
            String aString = Generator.generateAString(1000);

            byte[] actual = stringToByteConverter.convert(aString);

            assertThat(actual).isNotNull();

            assertThat(new String(actual, StandardCharsets.UTF_8)).isEqualTo(aString);
        });
    }

    @Test
    void convert_whenNull_thenReturnNull() {
        assertDoesNotThrow(() -> {
            assertThat(stringToByteConverter.convert(null)).isNull();
        });
    }
}