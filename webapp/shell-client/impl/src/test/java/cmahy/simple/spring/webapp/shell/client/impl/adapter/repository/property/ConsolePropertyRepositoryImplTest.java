package cmahy.simple.spring.webapp.shell.client.impl.adapter.repository.property;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.shell.client.impl.adapter.config.properties.ApplicationProperties;
import cmahy.simple.spring.webapp.shell.client.impl.application.vo.property.console.ConsolePropertyVo;
import cmahy.simple.spring.webapp.shell.client.impl.application.vo.property.console.OutputPropertyVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ConsolePropertyRepositoryImplTest {

    private ConsolePropertyRepositoryImpl consolePropertyRepositoryImpl;

    @Test
    void consoleProperty() {
        assertDoesNotThrow(() -> {
            ApplicationProperties applicationProperties = mock(ApplicationProperties.class);
            ConsolePropertyVo consolePropertyVo = mock(ConsolePropertyVo.class);

            when(applicationProperties.console()).thenReturn(consolePropertyVo);

            consolePropertyRepositoryImpl = new ConsolePropertyRepositoryImpl(applicationProperties);

            Optional<ConsolePropertyVo> actual = consolePropertyRepositoryImpl.consoleProperty();

            assertThat(actual)
                .isNotEmpty()
                .hasValue(consolePropertyVo);
        });
    }

    @Test
    void consoleProperty_whenGivenValueIsNull_thenResultHasToBeEmpty() {
        assertDoesNotThrow(() -> {
            consolePropertyRepositoryImpl = new ConsolePropertyRepositoryImpl(null);

            Optional<ConsolePropertyVo> actual = consolePropertyRepositoryImpl.consoleProperty();

            assertThat(actual).isEmpty();
        });
    }

    @Test
    void findFormat() {
        assertDoesNotThrow(() -> {
            String expectedFormatter = Generator.generateAString(1000);
            consolePropertyRepositoryImpl = new ConsolePropertyRepositoryImpl(
                new ApplicationProperties(
                    new ConsolePropertyVo(
                        new OutputPropertyVo(
                            expectedFormatter
                        )
                    ),
                    null,
                    null,
                    null
                )
            );

            Optional<String> actual = consolePropertyRepositoryImpl.findFormat();

            assertThat(actual)
                .isNotEmpty()
                .hasValue(expectedFormatter);
        });
    }

    @ParameterizedTest
    @MethodSource("emptyValues")
    void findFormat_whenGivenValueIsNull_thenResultHasToBeEmpty(ConsolePropertyVo consolePropertyVo) {
        assertDoesNotThrow(() -> {
            consolePropertyRepositoryImpl = new ConsolePropertyRepositoryImpl(new ApplicationProperties(
                consolePropertyVo, null, null, null
            ));

            Optional<String> actual = consolePropertyRepositoryImpl.findFormat();

            assertThat(actual).isEmpty();
        });
    }

    protected static Stream<ConsolePropertyVo> emptyValues() {
        return Stream.of(
            null,
            new ConsolePropertyVo(null),
            new ConsolePropertyVo(new OutputPropertyVo(null))
        );
    }
}