package cmahy.webapp.shell.client.impl.application.query;

import cmahy.common.annotation.Query;
import cmahy.webapp.shell.client.impl.application.service.GenerateHelpMessage;
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
