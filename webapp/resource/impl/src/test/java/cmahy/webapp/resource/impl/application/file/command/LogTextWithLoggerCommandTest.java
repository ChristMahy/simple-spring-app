package cmahy.webapp.resource.impl.application.file.command;

import cmahy.common.helper.Generator;
import cmahy.webapp.resource.impl.application.file.repository.LoggerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LogTextWithLoggerCommandTest {

    @Mock
    private LoggerRepository loggerRepository;

    @InjectMocks
    private LogTextWithLoggerCommand logTextWithLoggerCommand;

    @Test
    void execute() {
        assertDoesNotThrow(() -> {
            String someText = Generator.generateAString();

            logTextWithLoggerCommand.execute(someText);

            verify(loggerRepository).save(someText);
        });
    }
}