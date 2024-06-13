package cmahy.webapp.resource.impl.adapter.taco.shop.scheduler;

import cmahy.webapp.resource.impl.adapter.taco.shop.properties.ingredient.IngredientProperties;
import cmahy.webapp.resource.taco.shop.vo.output.IngredientOutputVo;
import cmahy.webapp.resource.taco.shop.vo.output.IngredientPageOutputVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@ConditionalOnProperty(value = "application.taco.ingredients.external-resource.scheduler.enable", havingValue = "true")
public class IngredientScheduler {

    private static final Logger LOG = LoggerFactory.getLogger(IngredientScheduler.class);

    private final IngredientProperties ingredientProperties;
    private final RestTemplate commonRestTemplate;

    public IngredientScheduler(
        IngredientProperties ingredientProperties,
        RestTemplate commonRestTemplate
    ) {
        this.ingredientProperties = ingredientProperties;
        this.commonRestTemplate = commonRestTemplate;
    }

    @Scheduled(
        initialDelayString = "${application.taco.ingredients.external-resource.scheduler.initial-delay:5000}",
        fixedRateString = "${application.taco.ingredients.external-resource.scheduler.fixed-rate:5000}"
    )
    public void runGetAllQuery() {
        LOG.info("Run ingredient template");

        try {
            IngredientPageOutputVo forObject = commonRestTemplate.getForObject(
                UriComponentsBuilder.fromHttpUrl(ingredientProperties.externalResource().baseUrl() + "/ingredient")
                    .queryParam("page-number", 0)
                    .queryParam("page-size", Integer.MAX_VALUE)
                    .encode()
                    .toUriString(),
                IngredientPageOutputVo.class
            );

            if (forObject != null) {
                LOG.info("Ingredient list size <{}/{}>", forObject.content().size(), forObject.totalElements());
                for (IngredientOutputVo ingredient : forObject.content()) {
                    LOG.info("Ingredient: {}", ingredient);
                }
            }
        } catch(Exception exce) {
            LOG.error(exce.getMessage(), exce);
        }
    }
}
