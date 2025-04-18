package cmahy.simple.spring.webapp.taco.shop.kernel.application.service;

import cmahy.simple.spring.webapp.taco.shop.kernel.application.repository.IngredientRepository;
import cmahy.simple.spring.webapp.taco.shop.kernel.application.repository.TacoRepository;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.Taco;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.id.IngredientId;
import jakarta.inject.Named;

import java.util.Optional;

@Named
public class VerifyIngredientUsage {

    private final IngredientRepository<Ingredient> ingredientRepository;
    private final TacoRepository<Taco> tacoRepository;

    public VerifyIngredientUsage(IngredientRepository ingredientRepository, TacoRepository tacoRepository) {
        this.ingredientRepository = ingredientRepository;
        this.tacoRepository = tacoRepository;
    }

    public boolean execute(IngredientId id) {
        Optional<?> foundWithId = ingredientRepository.findById(id);

        if (foundWithId.isPresent()) {
            return !tacoRepository.findByIngredientId(id).isEmpty();
        }

        return false;
    }
}
