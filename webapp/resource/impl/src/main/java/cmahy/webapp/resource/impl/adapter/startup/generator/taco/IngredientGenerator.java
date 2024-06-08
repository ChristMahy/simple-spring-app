package cmahy.webapp.resource.impl.adapter.startup.generator.taco;

import cmahy.webapp.resource.impl.application.taco.shop.command.CreateIngredientCommand;
import cmahy.webapp.resource.impl.application.taco.shop.repository.IngredientRepository;
import cmahy.webapp.resource.impl.application.taco.shop.vo.input.IngredientCreateInputAppVo;
import cmahy.webapp.resource.impl.domain.taco.Ingredient;
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
        createIngredientCommand.execute(new IngredientCreateInputAppVo("Flour Tortilla", Ingredient.Type.WRAP.name()));
        createIngredientCommand.execute(new IngredientCreateInputAppVo("Corn Tortilla", Ingredient.Type.WRAP.name()));
        createIngredientCommand.execute(new IngredientCreateInputAppVo("Ground Beef", Ingredient.Type.PROTEIN.name()));
        createIngredientCommand.execute(new IngredientCreateInputAppVo("Carnitas", Ingredient.Type.PROTEIN.name()));
        createIngredientCommand.execute(new IngredientCreateInputAppVo("Diced Tomatoes", Ingredient.Type.VEGGIES.name()));
        createIngredientCommand.execute(new IngredientCreateInputAppVo("Lettuce", Ingredient.Type.VEGGIES.name()));
        createIngredientCommand.execute(new IngredientCreateInputAppVo("Cheddar", Ingredient.Type.CHEESE.name()));
        createIngredientCommand.execute(new IngredientCreateInputAppVo("Monterrey Jack", Ingredient.Type.CHEESE.name()));
        createIngredientCommand.execute(new IngredientCreateInputAppVo("Salsa", Ingredient.Type.SAUCE.name()));
        createIngredientCommand.execute(new IngredientCreateInputAppVo("Sour Cream", Ingredient.Type.SAUCE.name()));
    }
}
