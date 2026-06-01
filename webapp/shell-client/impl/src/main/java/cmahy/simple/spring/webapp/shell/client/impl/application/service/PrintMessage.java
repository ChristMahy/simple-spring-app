package cmahy.simple.spring.webapp.shell.client.impl.application.service;

import cmahy.simple.spring.webapp.shell.client.impl.application.service.io.PrintStream;
import jakarta.inject.Named;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

@Named
public class PrintMessage {

    protected static final String WARNING_MESSAGE = "If you want log something, specify one message !!!";

    private final PrintStream printStream;

    public PrintMessage(PrintStream printStream) {
        this.printStream = printStream;
    }

    public Boolean execute(String message) {
        Optional<String> messageBlankEscaped = Optional.ofNullable(message)
            .filter(StringUtils::isNotBlank);

        printStream.write(
            messageBlankEscaped.orElse(WARNING_MESSAGE)
        );

        return messageBlankEscaped.isPresent();
    }
}
