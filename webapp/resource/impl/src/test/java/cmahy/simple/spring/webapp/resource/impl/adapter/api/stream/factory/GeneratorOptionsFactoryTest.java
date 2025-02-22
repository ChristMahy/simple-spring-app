package cmahy.simple.spring.webapp.resource.impl.adapter.api.stream.factory;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.resource.impl.application.stream.vo.option.GeneratorQueryOption;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
class GeneratorOptionsFactoryTest {

    @InjectMocks
    private GeneratorOptionsFactory generatorOptionsFactory;

    @Test
    void singleFile() {
        assertDoesNotThrow(() -> {
            var expectedOnFailureValue = Generator.randomBoolean();

            GeneratorQueryOption actual = generatorOptionsFactory.singleFile(expectedOnFailureValue);

            assertThat(actual).isNotNull();
            assertThat(actual.onFailure()).isEqualTo(expectedOnFailureValue);
            assertThat(actual.stringSize()).hasValue(5 * 1024);
            assertThat(actual.elementsSize()).hasValue(1024);
        });
    }

    @Test
    void singleFile_whenGivenValueIsNull_thenReplaceWithDefaultValue() {
        assertDoesNotThrow(() -> {
            GeneratorQueryOption actual = generatorOptionsFactory.singleFile(null);

            assertThat(actual).isNotNull();
            assertThat(actual.onFailure()).isEqualTo(Boolean.FALSE);
            assertThat(actual.stringSize()).hasValue(5 * 1024);
            assertThat(actual.elementsSize()).hasValue(1024);
        });
    }

    @Test
    void zip() {
        assertDoesNotThrow(() -> {
            var expectedOnFailureValue = Generator.randomBoolean();

            GeneratorQueryOption actual = generatorOptionsFactory.zip(expectedOnFailureValue);

            assertThat(actual).isNotNull();
            assertThat(actual.onFailure()).isEqualTo(expectedOnFailureValue);
            assertThat(actual.stringSize()).hasValue(5 * 1024 * 1024);
            assertThat(actual.elementsSize()).hasValue(1024);
        });
    }

    @Test
    void zip_whenGivenValueIsNull_thenReplaceWithDefaultValue() {
        assertDoesNotThrow(() -> {
            GeneratorQueryOption actual = generatorOptionsFactory.zip(null);

            assertThat(actual).isNotNull();
            assertThat(actual.onFailure()).isEqualTo(Boolean.FALSE);
            assertThat(actual.stringSize()).hasValue(5 * 1024 * 1024);
            assertThat(actual.elementsSize()).hasValue(1024);
        });
    }
}