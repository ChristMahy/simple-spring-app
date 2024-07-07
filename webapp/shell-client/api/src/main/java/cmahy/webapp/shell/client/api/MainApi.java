package cmahy.webapp.shell.client.api;

import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(
    subcommands = {
        VersionApi.class,
        OtherApi.class
    }
)
public abstract class MainApi implements Callable<Integer> {
}
