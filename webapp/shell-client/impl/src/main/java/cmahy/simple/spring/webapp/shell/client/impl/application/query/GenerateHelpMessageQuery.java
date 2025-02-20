package cmahy.simple.spring.webapp.shell.client.impl.application.query;

import cmahy.simple.spring.common.annotation.Query;
import cmahy.simple.spring.webapp.shell.client.impl.application.service.GenerateHelpMessage;
import jakarta.inject.Named;

import java.util.Optional;

@Query
@Named
public class GenerateHelpMessageQuery {

    private final GenerateHelpMessage generateHelpMessage;

    public GenerateHelpMessageQuery(GenerateHelpMessage generateHelpMessage) {
        this.generateHelpMessage = generateHelpMessage;
    }

    public Optional<String> execute() {
        return generateHelpMessage.execute();
    }
}
