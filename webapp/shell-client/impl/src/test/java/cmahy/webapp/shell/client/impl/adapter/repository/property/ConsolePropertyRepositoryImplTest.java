package cmahy.webapp.shell.client.impl.adapter.repository.property;

import cmahy.common.helper.Generator;
import cmahy.webapp.shell.client.impl.application.vo.property.console.ConsolePropertyVo;
import cmahy.webapp.shell.client.impl.application.vo.property.console.OutputPropertyVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

class ConsolePropertyRepositoryImplTest {

    private ConsolePropertyRepositoryImpl consolePropertyRepositoryImpl;

    @Test
    void consoleProperty() {
        assertDoesNotThrow(() -> {
            ConsolePropertyVo consolePropertyVo = mock(ConsolePropertyVo.class);

            consolePropertyRepositoryImpl = new ConsolePropertyRepositoryImpl(consolePropertyVo);

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
                new ConsolePropertyVo(
                    new OutputPropertyVo(
                        expectedFormatter
                    )
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
            consolePropertyRepositoryImpl = new ConsolePropertyRepositoryImpl(consolePropertyVo);

            Optional<String> actual = consolePropertyRepositoryImpl.findFormat();

            assertThat(actual).isEmpty();
        });
    }

    protected static Stream<ConsolePropertyVo> emptyValues() {
        return Stream.of(
            new ConsolePropertyVo(null),
            new ConsolePropertyVo(new OutputPropertyVo(null))
        );
    }
}