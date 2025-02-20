package cmahy.webapp.shell.client.impl.adapter.api.taco.shop.ingredient;

import cmahy.common.entity.page.DefaultEntityPageableImpl;
import cmahy.simple.spring.webapp.shell.client.api.taco.shop.ingredient.IngredientApi;
import cmahy.webapp.shell.client.impl.adapter.config.properties.ingredient.IngredientProperties;
import cmahy.webapp.shell.client.impl.application.query.PrintMessageQuery;
import cmahy.webapp.shell.client.impl.application.repository.property.ConsolePropertyRepository;
import cmahy.webapp.taco.shop.kernel.application.query.GetAllRemoteIngredientPagedQuery;
import cmahy.webapp.taco.shop.kernel.vo.output.IngredientPageOutputVo;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

@Named
public class IngredientApiImpl extends IngredientApi {

    private static final Logger LOG = LoggerFactory.getLogger(IngredientApiImpl.class);

    private final PrintMessageQuery printMessageQuery;
    private final ConsolePropertyRepository consolePropertyRepository;
    private final GetAllRemoteIngredientPagedQuery getAllIngredientPagedQuery;
    private final IngredientProperties ingredientProperties;

    public IngredientApiImpl(
        PrintMessageQuery printMessageQuery,
        ConsolePropertyRepository consolePropertyRepository,
        GetAllRemoteIngredientPagedQuery getAllIngredientPagedQuery,
        IngredientProperties ingredientProperties
    ) {
        this.printMessageQuery = printMessageQuery;
        this.consolePropertyRepository = consolePropertyRepository;
        this.getAllIngredientPagedQuery = getAllIngredientPagedQuery;
        this.ingredientProperties = ingredientProperties;
    }

    @Override
    public Integer call() throws Exception {
        try {
            LOG.info("Main app menu started.");

            try (
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                PrintStream ps = new PrintStream(baos, true, StandardCharsets.UTF_8)
            ) {
                CommandLine.usage(this, ps);

                printMessageQuery.execute(String.format(
                    consolePropertyRepository
                        .findFormat()
                        .orElse("%s"),
                    baos.toString(StandardCharsets.UTF_8)
                ));
            }

            LOG.info("Main app menu has been finished successfully.");

            return 0;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);

            return 1;
        }
    }

    @Override
    public Integer getAll() {
        try {
            LOG.info("Main app menu started.");

            Integer pageSize = ingredientProperties
                .externalResource()
                .pageSize()
                .orElse(5);
            Integer pageNumber = 0;

            boolean canContinue;

            do {
                LOG.info("Get some ingredients <{}> - <{}>", pageNumber, pageSize);

                IngredientPageOutputVo ingredients = getAllIngredientPagedQuery.execute(new DefaultEntityPageableImpl(pageNumber, pageSize));

                LOG.info("Received <{}> ingredients, total <{}>", ingredients.content().size(), ingredients.totalElements());

                ingredients.content().forEach(ingredient -> {
                    printMessageQuery.execute(String.format(
                        consolePropertyRepository
                            .findFormat()
                            .orElse("%s"),
                        ingredient
                    ));
                });

                pageNumber++;

                canContinue = (pageNumber * pageSize) < ingredients.totalElements();
            } while (canContinue);

            LOG.info("Main app menu has been finished successfully.");

            return 0;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);

            return 1;
        }
    }
}
