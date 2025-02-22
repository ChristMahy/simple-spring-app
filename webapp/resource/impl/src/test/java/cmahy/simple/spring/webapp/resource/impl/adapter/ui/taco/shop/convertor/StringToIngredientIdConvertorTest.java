package cmahy.simple.spring.webapp.resource.impl.adapter.ui.taco.shop.convertor;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.id.IngredientId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
class StringToIngredientIdConvertorTest {

    @InjectMocks
    private StringToIngredientIdConvertor stringToIngredientIdConvertor;

    @Test
    void convert() {
        assertDoesNotThrow(() -> {
            UUID id = Generator.randomUUID();

            IngredientId actual = stringToIngredientIdConvertor.convert(id.toString());

            assertThat(actual).isNotNull();
            assertThat(actual.value()).isEqualTo(id);
        });
    }

    @Test
    void convert_onNullAsGivenValue_thenShouldReturnNull() {
        assertDoesNotThrow(() -> {
            assertThat(stringToIngredientIdConvertor.convert(null)).isNull();
        });
    }

    @Test
    void convert_onBlankAsGivenValue_thenShouldReturnNull() {
        assertDoesNotThrow(() -> {
            assertThat(stringToIngredientIdConvertor.convert("       ")).isNull();
        });
    }

    @Test
    void convert_onEmptyAsGivenValue_thenShouldReturnNull() {
        assertDoesNotThrow(() -> {
            assertThat(stringToIngredientIdConvertor.convert("")).isNull();
        });
    }
}