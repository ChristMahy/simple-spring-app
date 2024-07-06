package cmahy.webapp.shell.client.impl.adapter.api;

import cmahy.webapp.shell.client.api.MainApi;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import picocli.CommandLine;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

@Named
public class MainApiImpl extends MainApi {

    private static final Logger LOG = LoggerFactory.getLogger(MainApiImpl.class);

    private final String consoleFormatter;

    public MainApiImpl(
        @Value("${application.console.output.format}")
        String consoleFormatter
    ) {
        this.consoleFormatter = consoleFormatter;
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

                System.out.printf(consoleFormatter, baos.toString(StandardCharsets.UTF_8));
            }

            LOG.info("Main app menu has been finished successfully.");

            return 0;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);

            return 1;
        }
    }
}
