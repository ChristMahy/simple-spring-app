package cmahy.webapp.resource.impl.adapter.taco.shop.scheduler;

import cmahy.webapp.resource.impl.application.taco.external.query.GetAllExternalIngredientQuery;
import cmahy.webapp.resource.impl.domain.taco.external.IngredientExternal;
import cmahy.webapp.resource.impl.domain.taco.external.page.IngredientExternalPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@ConditionalOnProperty(value = "application.taco.ingredients.external-resource.scheduler.enable", havingValue = "true")
public class IngredientScheduler {

    private static final Logger LOG = LoggerFactory.getLogger(IngredientScheduler.class);

    private final GetAllExternalIngredientQuery getAllExternalIngredientQuery;

    public IngredientScheduler(GetAllExternalIngredientQuery getAllExternalIngredientQuery) {
        this.getAllExternalIngredientQuery = getAllExternalIngredientQuery;
    }

    @Scheduled(
        initialDelayString = "${application.taco.ingredients.external-resource.scheduler.initial-delay:5000}",
        fixedRateString = "${application.taco.ingredients.external-resource.scheduler.fixed-rate:5000}"
    )
    public void runGetAllQuery() {
        LOG.info("Run ingredient template");

        try {
            IngredientExternalPage pageIngredients = getAllExternalIngredientQuery.execute();

            if (Objects.nonNull(pageIngredients)) {
                LOG.info("Ingredient list size <{}/{}>", pageIngredients.content().size(), pageIngredients.totalElements());

                for (IngredientExternal ingredient : pageIngredients.content()) {
                    LOG.info("Ingredient: {}", ingredient);
                }
            }
        } catch(Throwable exce) {
            LOG.error(exce.getMessage(), exce);
        }
    }
}
