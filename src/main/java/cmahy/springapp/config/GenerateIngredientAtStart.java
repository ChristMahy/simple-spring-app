package cmahy.springapp.config;

import cmahy.springapp.domain.Ingredient;
import cmahy.springapp.repository.IngredientRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GenerateIngredientAtStart {
    private final IngredientRepository ingredientRepository;


    public GenerateIngredientAtStart(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Bean
    public ApplicationRunner loadIngredient() {
        return args -> {
            ingredientRepository.save(new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP).markAsNew());
            ingredientRepository.save(new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP).markAsNew());
            ingredientRepository.save(new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN).markAsNew());
            ingredientRepository.save(new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN).markAsNew());
            ingredientRepository.save(new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES).markAsNew());
            ingredientRepository.save(new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES).markAsNew());
            ingredientRepository.save(new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE).markAsNew());
            ingredientRepository.save(new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE).markAsNew());
            ingredientRepository.save(new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE).markAsNew());
            ingredientRepository.save(new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE).markAsNew());
        };
    }
}
