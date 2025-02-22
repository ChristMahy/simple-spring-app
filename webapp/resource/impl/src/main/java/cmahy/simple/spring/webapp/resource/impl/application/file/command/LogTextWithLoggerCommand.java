package cmahy.simple.spring.webapp.resource.impl.application.file.command;

import cmahy.simple.spring.common.annotation.Command;
import cmahy.simple.spring.webapp.resource.impl.application.file.repository.LoggerRepository;
import jakarta.inject.Named;

@Command
@Named
public class LogTextWithLoggerCommand {

    private final LoggerRepository loggerRepository;

    public LogTextWithLoggerCommand(LoggerRepository loggerRepository) {
        this.loggerRepository = loggerRepository;
    }

    public void execute(String line) {
        loggerRepository.save(line);
    }
}
