package cmahy.webapp.shell.client.impl.application.service;

import cmahy.common.helper.Generator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
class PrintMessageTest {

    @InjectMocks
    private PrintMessage printMessage;

    @Test
    void execute() {
        assertDoesNotThrow(() -> {
            try (
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                PrintStream printStream = new PrintStream(byteArrayOutputStream)
            ) {
                String messageToPrint = Generator.generateAString(1000);

                PrintStream oldOut = System.out;

                System.setOut(printStream);

                printMessage.execute(messageToPrint);

                assertThat(byteArrayOutputStream.toString()).isEqualTo(messageToPrint + System.lineSeparator());

                System.setOut(oldOut);
            }
        });
    }

    @Test
    void execute_onEmptyString_thenPrintWarningMessage() {
        assertDoesNotThrow(() -> {
            try (
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                PrintStream printStream = new PrintStream(byteArrayOutputStream)
            ) {
                PrintStream oldOut = System.out;

                System.setOut(printStream);

                printMessage.execute(null);

                assertThat(byteArrayOutputStream.toString()).isEqualTo(PrintMessage.WARNING_MESSAGE + System.lineSeparator());

                System.setOut(oldOut);
            }
        });
    }
}