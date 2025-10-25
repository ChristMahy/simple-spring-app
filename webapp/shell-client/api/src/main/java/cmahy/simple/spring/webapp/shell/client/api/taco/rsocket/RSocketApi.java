package cmahy.simple.spring.webapp.shell.client.api.taco.rsocket;

import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

@Command(
    name = "--r-socket",
    version = { "Version 1.0.0" },
    description = { "RSocket test" },
    mixinStandardHelpOptions = true,
    exitCodeOnExecutionException = 1
)
public abstract class RSocketApi  implements Callable<Integer> {

    @Command(
        name = "--request-response",
        aliases = { "-rr" },
        version = { "Version 1.0.0" },
        description = { "Get a randomized string through RSocket" },
        mixinStandardHelpOptions = true,
        exitCodeOnExecutionException = 1
    )
    public abstract Integer requestResponse();

    @Command(
        name = "--request-stream",
        aliases = { "-rs" },
        version = { "Version 1.0.0" },
        description = { "Get a list of randomized string through RSocket" },
        mixinStandardHelpOptions = true,
        exitCodeOnExecutionException = 1
    )
    public abstract Integer requestStream();

    @Command(
        name = "--fire-and-forget",
        aliases = { "-ff" },
        version = { "Version 1.0.0" },
        description = { "Fire and forget through RSocket" },
        mixinStandardHelpOptions = true,
        exitCodeOnExecutionException = 1
    )
    public abstract Integer fireAndForget();

    @Command(
        name = "--channel",
        aliases = { "-c" },
        version = { "Version 1.0.0" },
        description = { "Channel through RSocket" },
        mixinStandardHelpOptions = true,
        exitCodeOnExecutionException = 1
    )
    public abstract Integer channel();



}
