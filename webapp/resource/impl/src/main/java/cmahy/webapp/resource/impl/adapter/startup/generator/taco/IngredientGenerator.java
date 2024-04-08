package cmahy.webapp.resource.impl.adapter.startup.generator.taco;

import cmahy.webapp.resource.impl.adapter.taco.shop.repository.IngredientRepositoryImpl;
import cmahy.webapp.resource.impl.domain.taco.Ingredient;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class IngredientGenerator implements ApplicationRunner {

    private final IngredientRepositoryImpl ingredientRepository;

    public IngredientGenerator(
        IngredientRepositoryImpl ingredientRepository
    ) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        ingredientRepository.save(new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP));
        ingredientRepository.save(new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP));
        ingredientRepository.save(new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN));
        ingredientRepository.save(new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN));
        ingredientRepository.save(new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES));
        ingredientRepository.save(new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES));
        ingredientRepository.save(new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE));
        ingredientRepository.save(new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE));
        ingredientRepository.save(new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE));
        ingredientRepository.save(new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE));
    }
}
