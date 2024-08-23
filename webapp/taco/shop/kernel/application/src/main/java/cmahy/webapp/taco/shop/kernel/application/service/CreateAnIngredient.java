package cmahy.webapp.taco.shop.kernel.application.service;

import cmahy.webapp.taco.shop.kernel.application.mapper.output.IngredientOutputMapper;
import cmahy.webapp.taco.shop.kernel.application.repository.IngredientRepository;
import cmahy.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.webapp.taco.shop.kernel.exception.RequiredException;
import cmahy.webapp.taco.shop.kernel.exception.ingredient.IngredientDuplicateException;
import cmahy.webapp.taco.shop.kernel.vo.input.IngredientCreateInputVo;
import cmahy.webapp.taco.shop.kernel.vo.output.IngredientOutputVo;
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

    public IngredientOutputVo execute(IngredientCreateInputVo inputVo) throws IngredientDuplicateException, RequiredException {
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
