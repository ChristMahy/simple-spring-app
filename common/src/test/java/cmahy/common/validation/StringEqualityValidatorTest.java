package cmahy.common.validation;

import cmahy.common.helper.Generator;
import cmahy.common.validation.exception.PublicAccessorNotFoundException;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class StringEqualityValidatorTest {

    @InjectMocks
    private StringEqualityValidator stringEqualityValidator;

    @Mock
    ConstraintValidatorContext context;

    @Test
    void isValid() {
        assertDoesNotThrow(() -> {
            String aString = Generator.generateAString();

            SampleWorkingClassWithAnnotation shouldMatchEquality = new SampleWorkingClassWithAnnotation(
                aString,
                aString,
                aString,
                Generator.generateAString(),
                Generator.randomBoolean(),
                Generator.randomInt(),
                Generator.randomLong()
            );

            assertThat(stringEqualityValidator.isValid(shouldMatchEquality, context)).isTrue();
        });
    }

    @Test
    void isValid_onNullProvided_thenReturnTrue() {
        assertDoesNotThrow(() -> {
            assertThat(stringEqualityValidator.isValid(null, context)).isTrue();
        });
    }

    @Test
    void isValid_onAtLeastOneValueIsDifferent_thenShouldReturnFalse() {
        assertDoesNotThrow(() -> {
            String aString = Generator.generateAString();

            int i = Generator.randomInt(1, 3);

            SampleWorkingClassWithAnnotation shouldMatchEquality = new SampleWorkingClassWithAnnotation(
                i == 1 ? Generator.generateAString() : aString,
                i == 2 ? Generator.generateAString() : aString,
                i == 3 ? Generator.generateAString() : aString,
                Generator.generateAString(),
                Generator.randomBoolean(),
                Generator.randomInt(),
                Generator.randomLong()
            );

            assertThat(stringEqualityValidator.isValid(shouldMatchEquality, context)).isFalse();
        });
    }

    @StringEquality
    private record SampleWorkingClassWithAnnotation(
        @StringEqualityField
        String fieldOne,
        @StringEqualityField
        String fieldTwo,
        @StringEqualityField
        String fieldThird,
        String shouldNotBeTested,
        Boolean aBooleanValue,
        Integer aIntValue,
        Long aLongValue
    ) {}

    @Test
    void isValid_onIgnoreCase_thenShouldReturnTrue() {
        assertDoesNotThrow(() -> {
            String aString = Generator.generateAString(500).toLowerCase();

            int i = Generator.randomInt(1, 3);

            SampleWorkingIgnoreCaseClassWithAnnotation shouldMatchEquality = new SampleWorkingIgnoreCaseClassWithAnnotation(
                i == 1 ? aString.toUpperCase() : aString,
                i == 2 ? aString.toUpperCase() : aString,
                i == 3 ? aString.toUpperCase() : aString
            );

            assertThat(stringEqualityValidator.isValid(shouldMatchEquality, context)).isTrue();
        });
    }

    @Test
    void isValid_onIgnoreCaseAtLeastOneDifferent_thenShouldReturnFalse() {
        assertDoesNotThrow(() -> {
            String aString = Generator.generateAString(500).toLowerCase();

            int i = Generator.randomInt(1, 3);

            SampleWorkingIgnoreCaseClassWithAnnotation shouldMatchEquality = new SampleWorkingIgnoreCaseClassWithAnnotation(
                i == 1 ? Generator.generateAString(500).toUpperCase() : aString,
                i == 2 ? Generator.generateAString(500).toUpperCase() : aString,
                i == 3 ? Generator.generateAString(500).toUpperCase() : aString
            );

            assertThat(stringEqualityValidator.isValid(shouldMatchEquality, context)).isFalse();
        });
    }

    @StringEquality(ignoreCase = true)
    private record SampleWorkingIgnoreCaseClassWithAnnotation(
        @StringEqualityField
        String fieldOne,
        @StringEqualityField
        String fieldTwo,
        @StringEqualityField
        String fieldThird
    ) {}

    @Test
    void isValid_onFieldProvidedIsNotAString_thenShouldReturnFalse() {
        assertDoesNotThrow(() -> {
            String aString = Generator.generateAString();

            SampleNotWorkingOneFieldIsNotAStringClassWithAnnotation shouldMatchEquality = new SampleNotWorkingOneFieldIsNotAStringClassWithAnnotation(
                aString,
                aString,
                Generator.randomBoolean()
            );

            assertThat(stringEqualityValidator.isValid(shouldMatchEquality, context)).isFalse();
        });
    }

    @StringEquality
    private record SampleNotWorkingOneFieldIsNotAStringClassWithAnnotation(
        @StringEqualityField
        String fieldOne,
        @StringEqualityField
        String fieldTwo,
        @StringEqualityField
        Boolean fieldThird
    ) {}

    @Test
    void isValid_onNoAnnotationFound_thenShouldReturnTrue() {
        assertDoesNotThrow(() -> {
            SampleClassWithoutAnnotation sampleClassWithoutAnnotation = new SampleClassWithoutAnnotation(Generator.generateAString(), Generator.generateAString());

            assertThat(stringEqualityValidator.isValid(sampleClassWithoutAnnotation, context)).isTrue();
        });
    }

    @StringEquality
    private record SampleClassWithoutAnnotation(
        String fieldOne,
        String fieldTwo
    ) {}

    @Test
    void isValid_onNoPublicAccessor_thenReturnFalse() {
        assertDoesNotThrow(() -> {
            SampleNoPublicAccessor sampleNoPublicAccessor = new SampleNoPublicAccessor(
                Generator.generateAString(),
                Generator.generateAString()
            );

            assertThat(stringEqualityValidator.isValid(sampleNoPublicAccessor, context)).isFalse();
        });
    }

    @StringEquality
    private static final class SampleNoPublicAccessor {

        @StringEqualityField
        private final String fieldOne;

        @StringEqualityField
        private final String fieldTwo;

        public SampleNoPublicAccessor(String fieldOne, String fieldTwo) {
            this.fieldOne = fieldOne;
            this.fieldTwo = fieldTwo;
        }
    }

    @Test
    void isValid_onMoreThanOneAccessorFound_thenReturnFalse() {
        assertDoesNotThrow(() -> {
            SampleWithMoreThanOneAccessor sampleWithMoreThanOneAccessor = new SampleWithMoreThanOneAccessor(
                Generator.generateAString(),
                Generator.generateAString()
            );

            assertThat(stringEqualityValidator.isValid(sampleWithMoreThanOneAccessor, context)).isFalse();
        });
    }

    @StringEquality
    private record SampleWithMoreThanOneAccessor(
        @StringEqualityField
        String fieldOne,
        @StringEqualityField
        String fieldTwo
    ) {
        public String getFieldOne() {
            return fieldOne;
        }
    }

    @Test
    void isValid_onMoreThanOneAccessorFoundButOtherReturnAnotherType_thenReturnTrue() {
        assertDoesNotThrow(() -> {
            String aString = Generator.generateAString();

            SampleWithMoreThanOneAccessorWithDifferentReturnType sampleWithMoreThanOneAccessorWithDifferentReturnType = new SampleWithMoreThanOneAccessorWithDifferentReturnType(
                aString,
                aString
            );

            assertThat(stringEqualityValidator.isValid(sampleWithMoreThanOneAccessorWithDifferentReturnType, context)).isTrue();
        });
    }

    @StringEquality
    private record SampleWithMoreThanOneAccessorWithDifferentReturnType(
        @StringEqualityField
        String fieldOne,
        @StringEqualityField
        String fieldTwo
    ) {
        public Boolean getFieldOne() {
            return Generator.randomBoolean();
        }
    }

    @Test
    void isValid_onMoreThanOneAccessorFoundButOtherHasAnArgument_thenReturnTrue() {
        assertDoesNotThrow(() -> {
            String aString = Generator.generateAString();

            SampleWithMoreThanOneAccessorWithArgument sampleWithMoreThanOneAccessorWithArgument = new SampleWithMoreThanOneAccessorWithArgument(
                aString,
                aString
            );

            assertThat(stringEqualityValidator.isValid(sampleWithMoreThanOneAccessorWithArgument, context)).isTrue();
        });
    }

    @StringEquality
    private record SampleWithMoreThanOneAccessorWithArgument(
        @StringEqualityField
        String fieldOne,
        @StringEqualityField
        String fieldTwo
    ) {
        public String fieldOne(Boolean test) {
            return Generator.generateAString();
        }
    }
}