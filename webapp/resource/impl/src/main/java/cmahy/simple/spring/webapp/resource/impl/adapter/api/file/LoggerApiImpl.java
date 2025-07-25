package cmahy.simple.spring.webapp.resource.impl.adapter.api.file;

import cmahy.simple.spring.webapp.resource.api.file.LoggerApi;
import cmahy.simple.spring.webapp.resource.impl.application.file.command.LogTextWithLoggerCommand;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoggerApiImpl implements LoggerApi {

    private final LogTextWithLoggerCommand logTextWithLoggerCommand;

    public LoggerApiImpl(LogTextWithLoggerCommand logTextWithLoggerCommand) {
        this.logTextWithLoggerCommand = logTextWithLoggerCommand;
    }

    @Override
    @PreAuthorize("hasRole(@preAuthorizeAuthorities.GUEST)")
    public void logSomeText(String line) {
        logTextWithLoggerCommand.execute(line);
    }
}
