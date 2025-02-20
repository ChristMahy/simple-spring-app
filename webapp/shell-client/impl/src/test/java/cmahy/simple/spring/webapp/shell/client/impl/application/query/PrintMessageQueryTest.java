package cmahy.simple.spring.webapp.shell.client.impl.application.query;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.shell.client.impl.application.service.PrintMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PrintMessageQueryTest {

    @Mock
    private PrintMessage printMessage;

    @InjectMocks
    private PrintMessageQuery printMessageQuery;

    @Test
    void execute() {
        assertDoesNotThrow(() -> {
            String messageToPrint = Generator.generateAString();
            Boolean result = Generator.randomBoolean();

            when(printMessage.execute(messageToPrint)).thenReturn(result);

            Boolean actual = printMessageQuery.execute(messageToPrint);

            assertThat(actual).isEqualTo(result);
        });
    }
}