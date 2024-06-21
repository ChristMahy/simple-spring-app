package cmahy.webapp.resource.impl.adapter.startup.generator.taco;

import cmahy.webapp.resource.impl.application.taco.shop.repository.*;
import cmahy.webapp.resource.impl.domain.taco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.IntStream;

@Component
@Order(102)
@ConditionalOnProperty(name = "application.start-up.order.generate", havingValue = "true")
public class ClientOrderGenerator implements ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(ClientOrderGenerator.class);

    private final IngredientRepository ingredientRepository;
    private final TacoRepository tacoRepository;
    private final ClientOrderRepository clientOrderRepository;
    private final Optional<Integer> initialClientOrderSize;

    public ClientOrderGenerator(
        IngredientRepository ingredientRepository,
        TacoRepository tacoRepository,
        ClientOrderRepository clientOrderRepository,
        @Value("${application.start-up.order.initial-size:}") Optional<Integer> initialClientOrderSize
    ) {
        this.ingredientRepository = ingredientRepository;
        this.tacoRepository = tacoRepository;
        this.clientOrderRepository = clientOrderRepository;
        this.initialClientOrderSize = initialClientOrderSize;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        LOG.warn("Generate order on start up");

        IntStream.rangeClosed(1, initialClientOrderSize.orElse(1))
            .mapToObj(index -> {
                Taco taco1 = new Taco();

                taco1.setName("Taco " + index + ".1");
                taco1 = applyAllIngredientsToTaco(taco1);

                taco1 = tacoRepository.save(taco1);

                Taco taco2 = new Taco();

                taco2.setName("Taco " + index + ".2");
                taco2 = applyAllIngredientsToTaco(taco2);

                taco2 = tacoRepository.save(taco2);

                ClientOrder clientOrder = new ClientOrder();

                clientOrder.setDeliveryName("Taco order " + index);
                clientOrder.setDeliveryStreet("Street " + index);
                clientOrder.setDeliveryState("State " + index);
                clientOrder.setDeliveryCity("City " + index);
                clientOrder.setDeliveryZip("5000");
                clientOrder.setCcNumber("1234123412341234");
                clientOrder.setCcExpiration("08/24");
                clientOrder.setCcCVV("123");

                clientOrder.addTaco(taco1);
                clientOrder.addTaco(taco2);

                return clientOrder;
            })
            .map(clientOrderRepository::save)
            .forEach(clientOrder -> {});
    }

    private Taco applyAllIngredientsToTaco(Taco taco) {
        Set<Ingredient> allIngredients = new HashSet<>();

        for (IngredientType ingredientType : IngredientType.values()) {
            allIngredients.addAll(ingredientRepository.findByType(ingredientType));
        }

        taco.setIngredients(
            allIngredients.stream()
                .sorted(Comparator
                    .comparing(o -> ((Ingredient) o).getType().name())
                    .thenComparing(o -> ((Ingredient) o).getName())
                )
                .toList());

        return taco;
    }
}
