package cmahy.webapp.resource.impl.application.stream.vo.option;

import cmahy.common.helper.Generator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class GeneratorQueryOptionTest {

    @Test
    void elementsSizeOrDefaultAboveZero() {
        assertDoesNotThrow(() -> {
            var askFailure = Generator.randomBoolean();
            var stringSize = Generator.randomInt(1, Integer.MAX_VALUE);
            var elementsSize = Generator.randomInt(1, Integer.MAX_VALUE);

            var options = new GeneratorQueryOption(
                askFailure,
                Optional.of(stringSize),
                Optional.of(elementsSize)
            );

            assertThat(options.onFailure()).isEqualTo(askFailure);
            assertThat(options.stringSizeOrDefaultAboveZero()).isEqualTo(stringSize);
            assertThat(options.elementsSizeOrDefaultAboveZero()).isEqualTo(elementsSize);
        });
    }

    @Test
    void elementsSizeOrDefaultAboveZero_withDefinedDefaultValue_shouldReturnDefinedDefaultValue() {
        assertDoesNotThrow(() -> {
            var askFailure = Generator.randomBoolean();
            var stringSize = Generator.randomInt(200000, Integer.MAX_VALUE);
            var elementsSize = Generator.randomInt(200000, Integer.MAX_VALUE);

            var options = new GeneratorQueryOption(
                askFailure,
                Optional.empty(),
                Optional.empty()
            );

            assertThat(options.stringSizeOrDefaultAboveZero(stringSize)).isEqualTo(stringSize);
            assertThat(options.elementsSizeOrDefaultAboveZero(elementsSize)).isEqualTo(elementsSize);
        });
    }

    @Test
    void elementsSizeOrDefaultAboveZero_withNegativeDefinedDefaultValue_shouldReturnOptionValue() {
        assertDoesNotThrow(() -> {
            var askFailure = Generator.randomBoolean();
            var stringSize = Generator.randomInt(Integer.MIN_VALUE, 0);
            var elementsSize = Generator.randomInt(Integer.MIN_VALUE, 0);

            var options = new GeneratorQueryOption(
                askFailure,
                Optional.of(stringSize),
                Optional.of(elementsSize)
            );

            assertThat(options.stringSizeOrDefaultAboveZero(stringSize)).isEqualTo(5 * 1024);
            assertThat(options.elementsSizeOrDefaultAboveZero(elementsSize)).isEqualTo(1024);
        });
    }
}