package cmahy.webapp.resource.impl.adapter.ui.taco.shop.convertor;

import cmahy.common.helper.Generator;
import cmahy.webapp.taco.shop.kernel.domain.id.IngredientId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
class StringToIngredientIdConvertorTest {

    @InjectMocks
    private StringToIngredientIdConvertor stringToIngredientIdConvertor;

    @Test
    void convert() {
        assertDoesNotThrow(() -> {
            String id = Generator.generateAString();

            IngredientId actual = stringToIngredientIdConvertor.convert(id);

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