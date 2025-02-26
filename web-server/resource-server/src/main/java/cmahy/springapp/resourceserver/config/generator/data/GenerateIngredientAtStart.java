package cmahy.springapp.resourceserver.config.generator.data;

import cmahy.springapp.resourceserver.domain.Ingredient;
import cmahy.springapp.resourceserver.domain.Taco;
import cmahy.springapp.resourceserver.repository.IngredientRepository;
import cmahy.springapp.resourceserver.repository.TacoRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;

@Configuration
public class GenerateIngredientAtStart implements ApplicationRunner {
    private final IngredientRepository ingredientRepository;
    private final TacoRepository tacoRepository;

    public GenerateIngredientAtStart(IngredientRepository ingredientRepository, TacoRepository tacoRepository) {
        this.ingredientRepository = ingredientRepository;
        this.tacoRepository = tacoRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
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

        Taco taco1 = new Taco();
        taco1.setName("Taco 1");
        taco1.setCreatedAt(Date.from(Instant.now()));
        taco1.setIngredients(Arrays.asList(
            flto, grbf, carn, srcr, slsa, ched
        ));
        tacoRepository.save(taco1);

        Taco taco2 = new Taco();
        taco2.setName("Taco 2");
        taco2.setCreatedAt(Date.from(Instant.now()));
        taco2.setIngredients(Arrays.asList(
            coto, grbf, ched, jack, srcr
        ));
        tacoRepository.save(taco2);

        Taco taco3 = new Taco();
        taco3.setName("Taco 3");
        taco3.setCreatedAt(Date.from(Instant.now()));
        taco3.setIngredients(Arrays.asList(
            flto, coto, tmto, letc, slsa
        ));
        tacoRepository.save(taco3);
    }
}
