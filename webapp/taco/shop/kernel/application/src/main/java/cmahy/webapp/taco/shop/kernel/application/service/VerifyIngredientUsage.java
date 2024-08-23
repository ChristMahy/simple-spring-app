package cmahy.webapp.taco.shop.kernel.application.service;

import cmahy.webapp.taco.shop.kernel.application.repository.IngredientRepository;
import cmahy.webapp.taco.shop.kernel.application.repository.TacoRepository;
import cmahy.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.webapp.taco.shop.kernel.domain.id.IngredientId;
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
        Optional<Ingredient> foundWithId = ingredientRepository.findById(id);

        if (foundWithId.isPresent()) {
            return !tacoRepository.findByIngredientId(id).isEmpty();
        }

        return false;
    }
}
