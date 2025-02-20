package cmahy.webapp.shell.client.impl.adapter.service;

import cmahy.simple.spring.webapp.shell.client.api.MainApi;
import jakarta.inject.Named;
import picocli.CommandLine;

@Named
public class CommandLineFactory {

    private final MainApi mainApi;
    private final CommandLine.IFactory factory;

    public CommandLineFactory(
        MainApi mainApi,
        CommandLine.IFactory factory
    ) {
        this.mainApi = mainApi;
        this.factory = factory;
    }

    public CommandLine create() {
        return new CommandLine(mainApi, factory);
    }
}
