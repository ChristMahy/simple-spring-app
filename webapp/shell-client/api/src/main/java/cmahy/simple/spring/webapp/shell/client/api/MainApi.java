package cmahy.simple.spring.webapp.shell.client.api;

import cmahy.simple.spring.webapp.shell.client.api.taco.shop.ingredient.IngredientApi;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(
    subcommands = {
        VersionApi.class,
        OtherApi.class,
        IngredientApi.class
    }
)
public abstract class MainApi implements Callable<Integer> {
}
