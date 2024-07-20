package cmahy.webapp.shell.client.impl.application.service;

import cmahy.webapp.shell.client.impl.application.repository.property.ConsolePropertyRepository;
import cmahy.webapp.shell.client.impl.application.repository.property.HelpPropertyRepository;
import cmahy.webapp.shell.client.impl.application.vo.property.help.GeneralPropertyVo;
import cmahy.webapp.shell.client.impl.application.vo.property.help.HelpPropertyVo;
import jakarta.inject.Named;

import java.util.Optional;

@Named
public class GenerateHelpMessage {

    private final ConsolePropertyRepository consolePropertyRepository;
    private final HelpPropertyRepository helpPropertyRepository;

    public GenerateHelpMessage(
        ConsolePropertyRepository consolePropertyRepository,
        HelpPropertyRepository helpPropertyRepository
    ) {
        this.consolePropertyRepository = consolePropertyRepository;
        this.helpPropertyRepository = helpPropertyRepository;
    }

    public Optional<String> execute() {
        return helpPropertyRepository.helpProperty()
            .map(HelpPropertyVo::general)
            .map(GeneralPropertyVo::message)
            .map(message -> String.format(
                consolePropertyRepository
                    .findFormat()
                    .orElse("%s"),
                message
            ));
    }
}
