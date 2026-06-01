package cmahy.simple.spring.webapp.shell.client.impl.application.service;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.shell.client.impl.application.service.io.PrintStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PrintMessageTest {

    @Mock
    private PrintStream printStream;

    @InjectMocks
    private PrintMessage printMessage;

    @Test
    void execute() {
        assertDoesNotThrow(() -> {

            String messageToPrint = Generator.generateAString(1000);


            printMessage.execute(messageToPrint);


            verify(printStream).write(messageToPrint);

        });
    }

    @Test
    void execute_onEmptyString_thenPrintWarningMessage() {
        assertDoesNotThrow(() -> {


            printMessage.execute(null);


            verify(printStream).write(PrintMessage.WARNING_MESSAGE);

        });
    }
}