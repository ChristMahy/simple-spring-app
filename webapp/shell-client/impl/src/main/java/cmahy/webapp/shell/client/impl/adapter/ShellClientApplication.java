package cmahy.webapp.shell.client.impl.adapter;

import cmahy.webapp.shell.client.api.MainApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import picocli.CommandLine;

@SpringBootApplication(scanBasePackages = { "cmahy.webapp.shell.client" })
public class ShellClientApplication implements CommandLineRunner, ExitCodeGenerator {

    private static final Logger LOG = LoggerFactory.getLogger(ShellClientApplication.class);

    public static void main(String[] args) {
        SpringApplication.exit(
            SpringApplication.run(
                ShellClientApplication.class, args
            )
        );
    }

    private Integer exitCode = 0;

    private final MainApi mainApi;
    private final CommandLine.IFactory factory;

    public ShellClientApplication(
        MainApi mainApi,
        CommandLine.IFactory factory
    ) {
        this.mainApi = mainApi;
        this.factory = factory;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            exitCode = new CommandLine(mainApi, factory).execute(args);
        } catch (Exception any) {
            LOG.error(any.getMessage(), any);

            exitCode = 1;
        }
    }

    @Override
    public int getExitCode() {
        return exitCode;
    }
}
