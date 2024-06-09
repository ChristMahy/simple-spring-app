package cmahy.webapp.resource.impl.application.taco.shop.service;

import cmahy.webapp.resource.impl.application.taco.shop.mapper.output.IngredientOutputMapper;
import cmahy.webapp.resource.impl.application.taco.shop.repository.IngredientRepository;
import cmahy.webapp.resource.impl.domain.taco.Ingredient;
import cmahy.webapp.resource.impl.exception.taco.IngredientDuplicateException;
import cmahy.webapp.resource.taco.shop.vo.input.IngredientCreateInputVo;
import cmahy.webapp.resource.taco.shop.vo.output.IngredientOutputVo;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.UUID;

@Named
public class CreateAnIngredient {

    private static final Logger LOG = LoggerFactory.getLogger(CreateAnIngredient.class);

    private final IngredientFactory ingredientFactory;
    private final IngredientRepository ingredientRepository;
    private final IngredientOutputMapper ingredientOutputMapper;

    public CreateAnIngredient(
        IngredientFactory ingredientFactory,
        IngredientRepository ingredientRepository,
        IngredientOutputMapper ingredientOutputMapper
    ) {
        this.ingredientFactory = ingredientFactory;
        this.ingredientRepository = ingredientRepository;
        this.ingredientOutputMapper = ingredientOutputMapper;
    }

    public IngredientOutputVo execute(IngredientCreateInputVo inputVo) {
        LOG.info("Create an ingredient from <{}>", inputVo);

        Ingredient ingredient = ingredientFactory
            .create(inputVo)
            .setId(UUID.randomUUID().toString());

        Optional<Ingredient> found = ingredientRepository.findByNameAndType(ingredient.getName(), ingredient.getType());

        if (found.isPresent()) {
            LOG.info("Ingredient already exists <{}>", ingredient);

            throw new IngredientDuplicateException(found.get());
        }

        return ingredientOutputMapper.map(
            ingredientRepository.save(ingredient)
        );
    }
}
