package cmahy.springapp.controller.rest;

import cmahy.springapp.domain.Ingredient;
import cmahy.springapp.repository.IngredientRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/ingredient", produces = APPLICATION_JSON_VALUE)
public class IngredientController {

    private final IngredientRepository ingredientRepository;

    public IngredientController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @GetMapping
    public Iterable<Ingredient> getAll() {
        return ingredientRepository.findAll();
    }
}
