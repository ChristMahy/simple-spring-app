package cmahy.webapp.resource.impl.adapter.api.file;

import cmahy.common.helper.Generator;
import cmahy.webapp.resource.impl.application.file.command.LogTextWithLoggerCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LoggerApiImplTest {

    @Mock
    private LogTextWithLoggerCommand logTextWithLoggerCommand;

    @InjectMocks
    private LoggerApiImpl loggerApiImpl;

    @Test
    void logSomeText() {
        assertDoesNotThrow(() -> {
            String someText = Generator.generateAString(10000);

            loggerApiImpl.logSomeText(someText);

            verify(logTextWithLoggerCommand).execute(someText);
        });
    }
}