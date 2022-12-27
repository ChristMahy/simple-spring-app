package cmahy.springapp.converter;

import cmahy.springapp.domain.Ingredient;
import cmahy.springapp.domain.IngredientUDT;
import cmahy.springapp.repository.IngredientRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IngredientByIdConverter implements Converter<String, IngredientUDT> {

    private final IngredientRepository ingredientRepository;

    public IngredientByIdConverter(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public IngredientUDT convert(String id) {
        return ingredientRepository
            .findById(id)
            .map(Ingredient::toUDT)
            .orElse(null);
    }
}
