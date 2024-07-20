package cmahy.webapp.shell.client.impl.adapter;

import cmahy.common.helper.Generator;
import cmahy.webapp.shell.client.impl.adapter.service.CommandLineFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import picocli.CommandLine;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShellClientCommandRunnerTest {

    @Mock
    private CommandLineFactory commandLineFactory;

    @InjectMocks
    private ShellClientCommandRunner shellClientCommandRunner;

    @Test
    void run() {
        assertDoesNotThrow(() -> {
            String[] args = Stream.generate(Generator::generateAString)
                .limit(Generator.randomInt(50, 100))
                .toArray(String[]::new);
            CommandLine commandLine = mock(CommandLine.class);

            when(commandLineFactory.create()).thenReturn(commandLine);

            shellClientCommandRunner.run(args);

            assertThat(shellClientCommandRunner.getExitCode()).isEqualTo(0);

            verify(commandLine).execute(args);
        });
    }

    @Test
    void run_onAnyError_thenExitCodeShouldBe1() {
        assertDoesNotThrow(() -> {
            String[] args = Stream.generate(Generator::generateAString)
                .limit(Generator.randomInt(50, 100))
                .toArray(String[]::new);
            CommandLine commandLine = mock(CommandLine.class);

            when(commandLineFactory.create()).thenReturn(commandLine);
            when(commandLine.execute(args)).thenAnswer(_ -> {
                throw new RuntimeException();
            });

            shellClientCommandRunner.run(args);

            assertThat(shellClientCommandRunner.getExitCode()).isEqualTo(1);
        });
    }

    @Test
    void getExitCode() {
        assertDoesNotThrow(() -> {
            assertThat(shellClientCommandRunner.getExitCode()).isEqualTo(0);
        });
    }
}