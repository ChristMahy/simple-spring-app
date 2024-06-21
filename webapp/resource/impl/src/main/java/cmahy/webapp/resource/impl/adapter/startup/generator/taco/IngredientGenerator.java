package cmahy.webapp.resource.impl.adapter.startup.generator.taco;

import cmahy.webapp.resource.impl.application.taco.shop.command.CreateIngredientCommand;
import cmahy.webapp.resource.impl.domain.taco.IngredientType;
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
        createIngredientCommand.execute(new IngredientCreateInputVo("Flour Tortilla", IngredientType.WRAP.name()));
        createIngredientCommand.execute(new IngredientCreateInputVo("Corn Tortilla", IngredientType.WRAP.name()));
        createIngredientCommand.execute(new IngredientCreateInputVo("Ground Beef", IngredientType.PROTEIN.name()));
        createIngredientCommand.execute(new IngredientCreateInputVo("Carnitas", IngredientType.PROTEIN.name()));
        createIngredientCommand.execute(new IngredientCreateInputVo("Diced Tomatoes", IngredientType.VEGGIES.name()));
        createIngredientCommand.execute(new IngredientCreateInputVo("Lettuce", IngredientType.VEGGIES.name()));
        createIngredientCommand.execute(new IngredientCreateInputVo("Cheddar", IngredientType.CHEESE.name()));
        createIngredientCommand.execute(new IngredientCreateInputVo("Monterrey Jack", IngredientType.CHEESE.name()));
        createIngredientCommand.execute(new IngredientCreateInputVo("Salsa", IngredientType.SAUCE.name()));
        createIngredientCommand.execute(new IngredientCreateInputVo("Sour Cream", IngredientType.SAUCE.name()));
    }
}
