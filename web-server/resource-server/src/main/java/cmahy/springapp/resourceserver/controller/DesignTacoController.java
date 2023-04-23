package cmahy.springapp.resourceserver.controller;

import cmahy.springapp.resourceserver.domain.Ingredient;
import cmahy.springapp.resourceserver.domain.Taco;
import cmahy.springapp.resourceserver.domain.TacoOrder;
import cmahy.springapp.resourceserver.repository.IngredientRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static cmahy.springapp.resourceserver.config.security.AuthorizationConstant.ROLE_USER;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
@PreAuthorize("isAuthenticated()")
public class DesignTacoController {

    private final IngredientRepository ingredientRepository;

    public DesignTacoController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        Iterable<Ingredient> ingredients = ingredientRepository.findAll();

        Arrays.stream(Ingredient.Type.values()).forEach(type -> {
            model.addAttribute(
                type.toString().toLowerCase(),
                filterByTypes((List<Ingredient>) ingredients, type)
            );
        });
    }

    private Iterable<Ingredient> filterByTypes(List<Ingredient> ingredients, Ingredient.Type type) {
        return ingredients
            .stream()
            .filter(ingredient -> ingredient.getType().equals(type))
            .collect(Collectors.toList());
    }

    @ModelAttribute(name = "tacoOrder")
    public TacoOrder order() {
        return new TacoOrder();
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('" + ROLE_USER + "')")
    public String showDesignForm() {
        return "design";
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('" + ROLE_USER + "')")
    public String processTaco(
        @Valid Taco taco,
        Errors errors,
        @ModelAttribute TacoOrder tacoOrder
    ) {
        if (errors.hasErrors()) {
            return "design";
        }

        tacoOrder.addTaco(taco);

        log.info("Processing taco: {}", taco);

        return "redirect:/orders/current";
    }
}
