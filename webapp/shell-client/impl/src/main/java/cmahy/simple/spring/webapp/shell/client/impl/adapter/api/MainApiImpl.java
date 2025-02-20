package cmahy.simple.spring.webapp.shell.client.impl.adapter.api;

import cmahy.simple.spring.webapp.shell.client.api.MainApi;
import cmahy.simple.spring.webapp.shell.client.impl.application.query.PrintMessageQuery;
import cmahy.simple.spring.webapp.shell.client.impl.application.repository.property.ConsolePropertyRepository;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

@Named
public class MainApiImpl extends MainApi {

    private static final Logger LOG = LoggerFactory.getLogger(MainApiImpl.class);

    private final PrintMessageQuery printMessageQuery;
    private final ConsolePropertyRepository consolePropertyRepository;

    public MainApiImpl(
        PrintMessageQuery printMessageQuery,
        ConsolePropertyRepository consolePropertyRepository
    ) {
        this.printMessageQuery = printMessageQuery;
        this.consolePropertyRepository = consolePropertyRepository;
    }

    @Override
    public Integer call() {
        try {
            LOG.info("Main app menu started.");

            try (
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                PrintStream ps = new PrintStream(baos, true, StandardCharsets.UTF_8)
            ) {
                CommandLine.usage(this, ps);

                printMessageQuery.execute(String.format(
                    consolePropertyRepository
                        .findFormat()
                        .orElse("%s"),
                    baos.toString(StandardCharsets.UTF_8)
                ));
            }

            LOG.info("Main app menu has been finished successfully.");

            return 0;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);

            return 1;
        }
    }
}
