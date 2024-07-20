package cmahy.webapp.shell.client.impl.application.query;

import cmahy.common.annotation.Query;
import cmahy.webapp.shell.client.impl.application.service.GenerateVersionMessage;
import jakarta.inject.Named;

import java.util.Optional;

@Query
@Named
public class GenerateVersionMessageQuery {

    private final GenerateVersionMessage generateVersionMessage;

    public GenerateVersionMessageQuery(
        GenerateVersionMessage generateVersionMessage
    ) {
        this.generateVersionMessage = generateVersionMessage;
    }

    public Optional<String> execute() {
        return generateVersionMessage.execute();
    }
}
