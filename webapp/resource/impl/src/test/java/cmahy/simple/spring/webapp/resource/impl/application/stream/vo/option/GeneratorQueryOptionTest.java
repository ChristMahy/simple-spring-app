package cmahy.simple.spring.webapp.resource.impl.application.stream.vo.option;

import cmahy.simple.spring.common.helper.Generator;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class GeneratorQueryOptionTest {

    @Test
    void onInstantiation_withDefinedValues_thenReturnDefinedValues() {
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
    void onInstantiationWithEmptyValue_withDefaultValue_shouldReturnDefaultValue() {
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
    void onInstantiationWithNegativeValues_withNegativeDefinedDefaultValue_shouldReturnOptionValue() {
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

    @Test
    void onInstantiationWithValues_withNullValues_shouldReturnInitialValues() {
        assertDoesNotThrow(() -> {
            var askFailure = Generator.randomBoolean();
            var stringSize = Generator.randomInt(1, Integer.MAX_VALUE);
            var elementsSize = Generator.randomInt(1, Integer.MAX_VALUE);

            var options = new GeneratorQueryOption(
                askFailure,
                Optional.of(stringSize),
                Optional.of(elementsSize)
            );

            assertThat(options.stringSizeOrDefaultAboveZero(null)).isEqualTo(stringSize);
            assertThat(options.elementsSizeOrDefaultAboveZero(null)).isEqualTo(elementsSize);
        });
    }
}