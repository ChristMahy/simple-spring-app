package cmahy.webapp.shell.client.impl.application.query;

import cmahy.common.annotation.Query;
import cmahy.webapp.shell.client.impl.application.service.PrintMessage;
import jakarta.inject.Named;

@Query
@Named
public class PrintMessageQuery {

    private final PrintMessage printMessage;

    public PrintMessageQuery(PrintMessage printMessage) {
        this.printMessage = printMessage;
    }

    public Boolean execute(String message) {
        return printMessage.execute(message);
    }
}
