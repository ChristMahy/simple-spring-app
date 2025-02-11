package cmahy.webapp.resource.impl.adapter.startup.generator.taco;

import cmahy.webapp.taco.shop.kernel.application.command.CreateIngredientCommand;
import cmahy.webapp.taco.shop.kernel.application.repository.IngredientRepository;
import cmahy.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.webapp.taco.shop.kernel.domain.IngredientType;
import cmahy.webapp.taco.shop.kernel.vo.input.IngredientCreateInputVo;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

@Component
@Order(101)
public class IngredientGenerator implements ApplicationRunner {

    private final CreateIngredientCommand createIngredientCommand;
    private final IngredientRepository<? extends Ingredient> ingredientRepository;

    public IngredientGenerator(
        CreateIngredientCommand createIngredientCommand,
        IngredientRepository ingredientRepository
    ) {
        this.createIngredientCommand = createIngredientCommand;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        List<IngredientCreateInputVo> ingredients = generateSomeIngredients()
            .filter(this::ingredientDoesNotExists)
            .toList();

        for (IngredientCreateInputVo ingredient : ingredients) {
            createIngredientCommand.execute(ingredient);
        }
    }

    private Stream<IngredientCreateInputVo> generateSomeIngredients() {
        return Stream.of(
            new IngredientCreateInputVo("Flour Tortilla", IngredientType.WRAP.name()),
            new IngredientCreateInputVo("Corn Tortilla", IngredientType.WRAP.name()),
            new IngredientCreateInputVo("Ground Beef", IngredientType.PROTEIN.name()),
            new IngredientCreateInputVo("Carnitas", IngredientType.PROTEIN.name()),
            new IngredientCreateInputVo("Diced Tomatoes", IngredientType.VEGGIES.name()),
            new IngredientCreateInputVo("Lettuce", IngredientType.VEGGIES.name()),
            new IngredientCreateInputVo("Cheddar", IngredientType.CHEESE.name()),
            new IngredientCreateInputVo("Monterrey Jack", IngredientType.CHEESE.name()),
            new IngredientCreateInputVo("Salsa", IngredientType.SAUCE.name()),
            new IngredientCreateInputVo("Sour Cream", IngredientType.SAUCE.name())
        );
    }

    private boolean ingredientDoesNotExists(IngredientCreateInputVo ingredient) {
        return ingredientRepository
            .findByNameAndType(ingredient.name(), IngredientType.valueOf(ingredient.type()))
            .isEmpty();
    }
}
