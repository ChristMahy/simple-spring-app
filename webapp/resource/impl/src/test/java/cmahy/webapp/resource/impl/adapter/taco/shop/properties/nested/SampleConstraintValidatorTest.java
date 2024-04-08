package cmahy.webapp.resource.impl.adapter.taco.shop.properties.nested;

import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static cmahy.common.helper.Generator.generateAString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
class SampleConstraintValidatorTest {

    @InjectMocks
    private SampleConstraintValidator sampleConstraintValidator;

    @Test
    void isValid() {
        assertDoesNotThrow(() -> {
            assertThat(
                sampleConstraintValidator.isValid("exact-match", mock(ConstraintValidatorContext.class))
            ).isTrue();
        });
    }

    @Test
    void isValid_whenNotEquals_thenReturnFalse() {
        assertDoesNotThrow(() -> {
            assertThat(
                sampleConstraintValidator.isValid(generateAString(), mock(ConstraintValidatorContext.class))
            ).isFalse();
        });
    }

    @Test
    void isValid_onAnyException_thenReturnFalse() {
        assertDoesNotThrow(() -> {
            try (MockedStatic<StringUtils> stringUtilsMocked = mockStatic(StringUtils.class)) {
                stringUtilsMocked.when(() -> StringUtils.equalsIgnoreCase(anyString(), anyString()))
                    .thenAnswer(invocationOnMock -> new Throwable(generateAString()));

                assertThat(
                    sampleConstraintValidator.isValid(generateAString(), mock(ConstraintValidatorContext.class))
                ).isFalse();
            }
        });
    }
}