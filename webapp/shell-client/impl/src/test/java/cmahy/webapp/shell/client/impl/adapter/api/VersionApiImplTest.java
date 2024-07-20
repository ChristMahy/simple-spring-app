package cmahy.webapp.shell.client.impl.adapter.api;

import cmahy.common.helper.Generator;
import cmahy.webapp.shell.client.impl.application.query.GenerateVersionMessageQuery;
import cmahy.webapp.shell.client.impl.application.query.PrintMessageQuery;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VersionApiImplTest {

    @Mock
    private PrintMessageQuery printMessageQuery;

    @Mock
    private GenerateVersionMessageQuery generateVersionMessageQuery;

    @InjectMocks
    private VersionApiImpl versionApiImpl;

    @Test
    void call() {
        assertDoesNotThrow(() -> {
            String messageToPrint = Generator.generateAString();

            when(generateVersionMessageQuery.execute()).thenReturn(Optional.of(messageToPrint));

            Integer actual = versionApiImpl.call();

            assertThat(actual).isEqualTo(0);
        });
    }

    @ParameterizedTest
    @MethodSource("aBunchOfException")
    void call_onAnyException_shouldBeThreadSafeAndReturnOne(Exception anException) {
        assertDoesNotThrow(() -> {
            when(generateVersionMessageQuery.execute()).thenAnswer(_ -> {
                throw anException;
            });

            Integer actual = versionApiImpl.call();

            assertThat(actual).isEqualTo(1);
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