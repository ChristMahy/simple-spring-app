package cmahy.springapp.clientserver.controller;

import cmahy.springapp.clientserver.domain.Ingredient;
import cmahy.springapp.clientserver.service.RestIngredientService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping(path = "/ingredient")
public class IngredientController {

    private final RestIngredientService ingredientService;

    public IngredientController(RestIngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SCOPE_ingredient.read')")
    public Flux<Ingredient> ingredients() {
        return ingredientService.getAll();
    }

//    @GetMapping
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SCOPE_ingredient.read')")
//    public String ingredients() {
//        return "Test it";
//    }
}
