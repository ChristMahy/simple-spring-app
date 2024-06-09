package cmahy.webapp.resource.impl.application.taco.shop.service;

import cmahy.webapp.resource.impl.application.taco.shop.repository.IngredientRepository;
import cmahy.webapp.resource.impl.application.taco.shop.repository.TacoRepository;
import cmahy.webapp.resource.impl.domain.taco.Ingredient;
import cmahy.webapp.resource.taco.shop.id.IngredientId;
import jakarta.inject.Named;

import java.util.Optional;

@Named
public class VerifyIngredientUsage {

    private final IngredientRepository ingredientRepository;
    private final TacoRepository tacoRepository;

    public VerifyIngredientUsage(IngredientRepository ingredientRepository, TacoRepository tacoRepository) {
        this.ingredientRepository = ingredientRepository;
        this.tacoRepository = tacoRepository;
    }

    public boolean execute(IngredientId id) {
        Optional<Ingredient> foundWithId = ingredientRepository.findById(id.value());

        if (foundWithId.isPresent()) {
            return !tacoRepository.findByIngredientId(id).isEmpty();
        }

        return false;
    }
}
