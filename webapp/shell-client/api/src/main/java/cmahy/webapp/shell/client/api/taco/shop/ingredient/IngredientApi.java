package cmahy.webapp.shell.client.api.taco.shop.ingredient;

import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(
    name = "--taco-ingredient",
    aliases = {"-ti"},
    version = {"Version 1.0.0"},
    description = {"Taco's ingredients"},
    mixinStandardHelpOptions = true,
    exitCodeOnExecutionException = 1
)
public abstract class IngredientApi implements Callable<Integer> {

    @CommandLine.Command(
        name = "--get-all",
        aliases = {"-a"},
        version = {"Version 1.0.0"},
        description = {"Get all ingredients"},
        mixinStandardHelpOptions = true,
        exitCodeOnExecutionException = 1
    )
    public abstract Integer getAll();
}
