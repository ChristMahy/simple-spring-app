package cmahy.springapp.resourceserver.controller.rest;

import cmahy.springapp.resourceserver.domain.Ingredient;
import cmahy.springapp.resourceserver.repository.IngredientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static cmahy.springapp.resourceserver.controller.rest.ApiUrlConstant.IngredientUrl.BASIC_INGREDIENT_URL;
import static cmahy.springapp.resourceserver.controller.rest.security.EndpointAuthorityConstant.Ingredient.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = BASIC_INGREDIENT_URL, produces = APPLICATION_JSON_VALUE)
@PreAuthorize("isAuthenticated()")
public class IngredientController {

    private final IngredientRepository ingredientRepository;

    public IngredientController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @GetMapping
    public Iterable<Ingredient> getAll() {
        return ingredientRepository.findAll();
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('SCOPE_" + WRITE + "')")
    @ResponseStatus(HttpStatus.CREATED)
    public Ingredient create(@RequestBody Ingredient ingredient) {
        return null;
//        return ingredientRepository.save(ingredient);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_" + DELETE + "')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") String id) {
        ingredientRepository.deleteById(id);
    }
}
