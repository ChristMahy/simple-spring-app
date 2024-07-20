package cmahy.webapp.shell.client.impl.adapter.api;

import cmahy.common.helper.Generator;
import cmahy.webapp.shell.client.impl.application.query.PrintMessageQuery;
import cmahy.webapp.shell.client.impl.application.repository.property.ConsolePropertyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import picocli.CommandLine;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MainApiImplTest {

    @Mock
    private PrintMessageQuery printMessageQuery;

    @Mock
    private ConsolePropertyRepository consolePropertyRepository;

    @InjectMocks
    private MainApiImpl mainApiImpl;

    @Test
    void call() {
        assertDoesNotThrow(() -> {
            try (MockedStatic<CommandLine> ignored = mockStatic(CommandLine.class)) {
                when(consolePropertyRepository.findFormat()).thenReturn(Optional.empty());

                Integer actual = mainApiImpl.call();

                assertThat(actual).isEqualTo(0);
            }
        });
    }

    @ParameterizedTest
    @MethodSource("aBunchOfException")
    void call_onAnyException_shouldBeThreadSafeAndReturnOne(Exception anException) {
        assertDoesNotThrow(() -> {
            try (MockedStatic<CommandLine> ignored = mockStatic(CommandLine.class)) {
                when(consolePropertyRepository.findFormat()).thenAnswer(_ -> {
                    throw anException;
                });

                Integer actual = mainApiImpl.call();

                assertThat(actual).isEqualTo(1);
            }
        });
    }

    protected static Stream<Exception> aBunchOfException() {
        return Stream.of(
            new Exception(Generator.generateAString()),
            new RuntimeException(Generator.generateAString()),
            new IllegalStateException(Generator.generateAString()),
            new IllegalArgumentException(Generator.generateAString()),
            new NullPointerException(Generator.generateAString())
        );
    }
}