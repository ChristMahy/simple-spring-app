package cmahy.simple.spring.webapp.resource.impl.adapter.taco.shop.scheduler;

import cmahy.simple.spring.common.entity.page.DefaultEntityPageableImpl;
import cmahy.simple.spring.webapp.taco.shop.kernel.application.query.GetAllRemoteIngredientPagedQuery;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.page.IngredientPage;
import cmahy.simple.spring.webapp.taco.shop.kernel.vo.output.IngredientOutputVo;
import cmahy.simple.spring.webapp.taco.shop.kernel.vo.output.IngredientPageOutputVo;
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

    private final GetAllRemoteIngredientPagedQuery getAllRemoteIngredientPagedQuery;

    public IngredientScheduler(GetAllRemoteIngredientPagedQuery getAllRemoteIngredientPagedQuery) {
        this.getAllRemoteIngredientPagedQuery = getAllRemoteIngredientPagedQuery;
    }

    @Scheduled(
        initialDelayString = "${application.taco.ingredients.external-resource.scheduler.initial-delay:5000}",
        fixedDelayString = "${application.taco.ingredients.external-resource.scheduler.fixed-delay:5000}"
    )
    public void runGetAllQuery() {
        LOG.info("Run ingredient template");

        try {
            IngredientPageOutputVo pageIngredients = getAllRemoteIngredientPagedQuery.execute(
                new DefaultEntityPageableImpl(0, Integer.MAX_VALUE)
            );

            if (Objects.nonNull(pageIngredients)) {
                LOG.info("Ingredient list size <{}/{}>", pageIngredients.content().size(), pageIngredients.totalElements());

                for (IngredientOutputVo ingredient : pageIngredients.content()) {
                    LOG.info("Ingredient: {}", ingredient);
                }
            }
        } catch(Throwable exce) {
            LOG.error(exce.getMessage(), exce);
        }
    }
}
