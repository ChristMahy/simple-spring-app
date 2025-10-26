package cmahy.simple.spring.webapp.resource.impl.adapter.actuator;

import cmahy.simple.spring.webapp.taco.shop.kernel.application.repository.IngredientRepository;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.IngredientType;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class TacoMetrics implements ApplicationListener<ApplicationStartedEvent> {

    private final MeterRegistry meterRegistry;
    private final IngredientRepository<? extends Ingredient> ingredientRepository;

    public TacoMetrics(
        MeterRegistry meterRegistry,
        IngredientRepository<? extends Ingredient> ingredientRepository
    ) {
        this.meterRegistry = meterRegistry;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {

        for (IngredientType ingredientType : IngredientType.values()) {

            for (Ingredient ingredient : ingredientRepository.findByType(ingredientType)) {

                meterRegistry
                    .counter(
                        "tacocloud",
                        "ingredient",
                        ingredient.getName()
                    )
                    .increment();

            }

        }

    }

}
