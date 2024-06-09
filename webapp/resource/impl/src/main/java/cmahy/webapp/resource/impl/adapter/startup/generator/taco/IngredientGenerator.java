package cmahy.webapp.resource.impl.adapter.startup.generator.taco;

import cmahy.webapp.resource.impl.application.taco.shop.command.CreateIngredientCommand;
import cmahy.webapp.resource.impl.domain.taco.Ingredient;
import cmahy.webapp.resource.taco.shop.vo.input.IngredientCreateInputVo;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(101)
public class IngredientGenerator implements ApplicationRunner {

    private final CreateIngredientCommand createIngredientCommand;

    public IngredientGenerator(CreateIngredientCommand createIngredientCommand) {
        this.createIngredientCommand = createIngredientCommand;
    }

    @Override
    public void run(ApplicationArguments args) {
        createIngredientCommand.execute(new IngredientCreateInputVo("Flour Tortilla", Ingredient.Type.WRAP.name()));
        createIngredientCommand.execute(new IngredientCreateInputVo("Corn Tortilla", Ingredient.Type.WRAP.name()));
        createIngredientCommand.execute(new IngredientCreateInputVo("Ground Beef", Ingredient.Type.PROTEIN.name()));
        createIngredientCommand.execute(new IngredientCreateInputVo("Carnitas", Ingredient.Type.PROTEIN.name()));
        createIngredientCommand.execute(new IngredientCreateInputVo("Diced Tomatoes", Ingredient.Type.VEGGIES.name()));
        createIngredientCommand.execute(new IngredientCreateInputVo("Lettuce", Ingredient.Type.VEGGIES.name()));
        createIngredientCommand.execute(new IngredientCreateInputVo("Cheddar", Ingredient.Type.CHEESE.name()));
        createIngredientCommand.execute(new IngredientCreateInputVo("Monterrey Jack", Ingredient.Type.CHEESE.name()));
        createIngredientCommand.execute(new IngredientCreateInputVo("Salsa", Ingredient.Type.SAUCE.name()));
        createIngredientCommand.execute(new IngredientCreateInputVo("Sour Cream", Ingredient.Type.SAUCE.name()));
    }
}
