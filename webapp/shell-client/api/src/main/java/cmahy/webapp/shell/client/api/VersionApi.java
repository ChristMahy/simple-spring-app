package cmahy.webapp.shell.client.api;

import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(
    name = "--app-version",
    aliases = {"-av"},
    version = {"Version 1.0.0"},
    description = {"Show current application version"},
    mixinStandardHelpOptions = true,
    exitCodeOnExecutionException = 1
)
public abstract class VersionApi implements Callable<Integer> {
}
