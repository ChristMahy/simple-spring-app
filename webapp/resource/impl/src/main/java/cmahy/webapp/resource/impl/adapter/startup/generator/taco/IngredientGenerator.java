package cmahy.webapp.resource.impl.adapter.startup.generator.taco;

import cmahy.webapp.resource.impl.adapter.taco.shop.repository.IngredientRepositoryImpl;
import cmahy.webapp.resource.impl.domain.taco.Ingredient;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IngredientGenerator implements ApplicationRunner {

    private final IngredientRepositoryImpl ingredientRepository;

    public IngredientGenerator(
        IngredientRepositoryImpl ingredientRepository
    ) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        Ingredient flto = ingredientRepository.save(new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP));
        Ingredient coto = ingredientRepository.save(new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP));
        Ingredient grbf = ingredientRepository.save(new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN));
        Ingredient carn = ingredientRepository.save(new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN));
        Ingredient tmto = ingredientRepository.save(new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES));
        Ingredient letc = ingredientRepository.save(new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES));
        Ingredient ched = ingredientRepository.save(new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE));
        Ingredient jack = ingredientRepository.save(new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE));
        Ingredient slsa = ingredientRepository.save(new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE));
        Ingredient srcr = ingredientRepository.save(new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE));
    }
}
