package cmahy.springapp.scheduler;

import cmahy.springapp.domain.Ingredient;
import org.slf4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public class IngredientScheduler {
    private static final Logger LOG = getLogger(IngredientScheduler.class);

    private final RestTemplate restTemplate;

    public IngredientScheduler(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Scheduled(initialDelay = 5000, fixedRate = 5000)
    public void runGetAllQuery() {
        LOG.info("Run ingredient template");

        try {
            Iterable forObject = restTemplate.getForObject("http://localhost:8080/ingredient", Iterable.class);

            if (forObject != null) {
                forObject.forEach(ingredient -> LOG.info("Ingredient: {}", ingredient));
            }
        } catch(Exception exce) {
            LOG.error(exce.getMessage(), exce);
        }
    }
}
