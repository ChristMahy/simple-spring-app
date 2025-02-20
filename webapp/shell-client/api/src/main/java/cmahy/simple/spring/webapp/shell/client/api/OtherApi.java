package cmahy.simple.spring.webapp.shell.client.api;

import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(
    name = "--app-help",
    aliases = {"-ah"},
    version = {"Help 1.0.0"},
    description = {"Show help menu"},
    mixinStandardHelpOptions = true,
    exitCodeOnExecutionException = 1
)
public abstract class OtherApi implements Callable<Integer> {
}
