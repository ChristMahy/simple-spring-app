package cmahy.simple.spring.webapp.resource.integration.test.persistence.api.command;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.resource.integration.test.persistence.api.exception.UnableToGenerateItemException;
import cmahy.simple.spring.webapp.resource.integration.test.persistence.api.repository.IngredientTestRepository;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.IngredientType;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.builder.IngredientBuilder;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.builder.factory.IngredientBuilderFactory;
import jakarta.inject.Named;

import java.util.*;

@Named
public class GenerateRandomIngredientCommand {

    private final IngredientTestRepository<Ingredient> ingredientTestRepository;
    private final IngredientBuilderFactory<Ingredient> ingredientBuilderFactory;

    public GenerateRandomIngredientCommand(
        IngredientTestRepository ingredientTestRepository,
        IngredientBuilderFactory ingredientBuilderFactory
    ) {
        this.ingredientTestRepository = ingredientTestRepository;
        this.ingredientBuilderFactory = ingredientBuilderFactory;
    }

    public <I extends Ingredient> I execute() throws UnableToGenerateItemException {

        return (I) execute(1).stream()
            .findFirst()
            .orElseThrow(() -> new UnableToGenerateItemException(Ingredient.class));

    }

    public <I extends Ingredient> Collection<I> execute(int length) throws UnableToGenerateItemException {

        try {

            List<I> ingredients = new ArrayList<>(length);

            for (int i = 0; i < length; i++) {

                IngredientBuilder<I> ingredientBuilder = (IngredientBuilder<I>) ingredientBuilderFactory.create();

                I ingredient = ingredientBuilder
                    .name(i + " => " + Generator.generateAString().trim())
                    .type(Generator.randomEnum(IngredientType.class))
                    .build();

                ingredient = (I) ingredientTestRepository.save(ingredient);

                ingredients.add(ingredient);

            }

            return ingredients;

        } catch (Exception e) {

            throw new UnableToGenerateItemException(Ingredient.class, e);

        }

    }
}
