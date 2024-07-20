package cmahy.webapp.shell.client.impl.application.query;

import cmahy.common.helper.Generator;
import cmahy.webapp.shell.client.impl.application.service.GenerateHelpMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GenerateHelpMessageQueryTest {

    @Mock
    private GenerateHelpMessage generateHelpMessage;

    @InjectMocks
    private GenerateHelpMessageQuery generateHelpMessageQuery;

    @Test
    void execute() {
        assertDoesNotThrow(() -> {
            String expectedValue = Generator.generateAString(1000);

            when(generateHelpMessage.execute()).thenReturn(Optional.of(expectedValue));

            Optional<String> actual = generateHelpMessageQuery.execute();

            assertThat(actual)
                .isNotEmpty()
                .hasValue(expectedValue);
        });
    }
}