package cmahy.simple.spring.webapp.shell.client.impl.application.service;

import jakarta.inject.Named;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

@Named
public class PrintMessage {

    protected static final String WARNING_MESSAGE = "If you want log something, specify one message !!!";

    public Boolean execute(String message) {
        Optional<String> messageBlankEscaped = Optional.ofNullable(message)
            .filter(StringUtils::isNotBlank);

        System.out.println(
            messageBlankEscaped.orElse(WARNING_MESSAGE)
        );

        return messageBlankEscaped.isPresent();
    }
}
