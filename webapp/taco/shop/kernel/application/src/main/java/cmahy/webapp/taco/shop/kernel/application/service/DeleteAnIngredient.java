package cmahy.webapp.taco.shop.kernel.application.service;

import cmahy.webapp.taco.shop.kernel.application.repository.IngredientRepository;
import cmahy.webapp.taco.shop.kernel.domain.id.IngredientId;
import cmahy.webapp.taco.shop.kernel.exception.ingredient.IngredientUsageElementOnDeletionException;
import jakarta.inject.Named;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

@Named
public class DeleteAnIngredient {

    private static final Logger LOG = LoggerFactory.getLogger(DeleteAnIngredient.class);

    private final IngredientRepository ingredientRepository;
    private final VerifyIngredientUsage verifyIngredientUsage;

    public DeleteAnIngredient(
        IngredientRepository ingredientRepository,
        VerifyIngredientUsage verifyIngredientUsage
    ) {
        this.ingredientRepository = ingredientRepository;
        this.verifyIngredientUsage = verifyIngredientUsage;
    }

    public void execute(IngredientId id) throws IngredientUsageElementOnDeletionException {
        LOG.info("Delete an ingredient <{}>", id);

        if (Objects.nonNull(id) && StringUtils.isNotBlank(id.value())) {
            if (verifyIngredientUsage.execute(id)) {
                throw new IngredientUsageElementOnDeletionException(id);
            }

            ingredientRepository.deleteById(id);
        }
    }
}
