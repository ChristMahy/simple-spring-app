package cmahy.webapp.shell.client.impl.adapter;

import cmahy.webapp.shell.client.impl.adapter.service.CommandLineFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ShellClientCommandRunner implements CommandLineRunner, ExitCodeGenerator {

    private static final Logger LOG = LoggerFactory.getLogger(ShellClientCommandRunner.class);

    private final CommandLineFactory commandLineFactory;

    private Integer exitCode = 0;
    private Instant startTime = Instant.now();

    public ShellClientCommandRunner(
        CommandLineFactory commandLineFactory
    ) {
        this.commandLineFactory = commandLineFactory;
    }

    @Override
    public void run(String... args) {
        try {
            exitCode = commandLineFactory.create().execute(args);
        } catch (Exception any) {
            LOG.error(any.getMessage(), any);

            exitCode = 1;
        }
    }

    @Override
    public int getExitCode() {
        LOG.info("Run in {} ms", Instant.now().toEpochMilli() - startTime.toEpochMilli());

        return exitCode;
    }
}
