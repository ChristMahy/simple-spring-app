package cmahy.simple.spring.webapp.shell.client.impl.application.query;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.shell.client.impl.application.service.GenerateVersionMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GenerateVersionMessageQueryTest {

    @Mock
    private GenerateVersionMessage generateVersionMessage;

    @InjectMocks
    private GenerateVersionMessageQuery generateVersionMessageQuery;

    @Test
    void execute() {
        assertDoesNotThrow(() -> {
            String expectedValue = Generator.generateAString(1000);

            when(generateVersionMessage.execute()).thenReturn(Optional.of(expectedValue));

            Optional<String> actual = generateVersionMessageQuery.execute();

            assertThat(actual)
                .isNotEmpty()
                .hasValue(expectedValue);
        });
    }
}