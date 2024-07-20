package cmahy.webapp.shell.client.impl.adapter.api;

import cmahy.common.helper.Generator;
import cmahy.webapp.shell.client.impl.application.query.GenerateHelpMessageQuery;
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
class OtherApiImplTest {

    @Mock
    private PrintMessageQuery printMessageQuery;

    @Mock
    private GenerateHelpMessageQuery generateHelpMessageQuery;

    @InjectMocks
    private OtherApiImpl otherApiImpl;

    @Test
    void call() {
        assertDoesNotThrow(() -> {
            String messageToPrint = Generator.generateAString();

            when(generateHelpMessageQuery.execute()).thenReturn(Optional.of(messageToPrint));

            Integer actual = otherApiImpl.call();

            assertThat(actual).isEqualTo(0);
        });
    }

    @ParameterizedTest
    @MethodSource("aBunchOfException")
    void call_onAnyException_shouldBeThreadSafeAndReturnOne(Exception anException) {
        assertDoesNotThrow(() -> {
            when(generateHelpMessageQuery.execute()).thenAnswer(_ -> {
                throw anException;
            });

            Integer actual = otherApiImpl.call();

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