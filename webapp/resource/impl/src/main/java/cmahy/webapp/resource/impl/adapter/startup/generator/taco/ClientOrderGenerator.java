package cmahy.webapp.resource.impl.adapter.startup.generator.taco;

import cmahy.webapp.taco.shop.kernel.domain.builder.factory.ClientOrderBuilderFactory;
import cmahy.webapp.taco.shop.kernel.domain.builder.factory.TacoBuilderFactory;
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

    private final IngredientRepository<Ingredient> ingredientRepository;
    private final TacoRepository<Taco> tacoRepository;
    private final ClientOrderRepository<ClientOrder> clientOrderRepository;
    private final TacoBuilderFactory<Taco> tacoBuilderFactory;
    private final ClientOrderBuilderFactory<ClientOrder> clientOrderBuilderFactory;
    private final Optional<Integer> initialClientOrderSize;

    public ClientOrderGenerator(
        IngredientRepository ingredientRepository,
        TacoRepository tacoRepository,
        ClientOrderRepository clientOrderRepository,
        TacoBuilderFactory tacoBuilderFactory,
        ClientOrderBuilderFactory clientOrderBuilderFactory,
        @Value("${application.start-up.order.initial-size:}") Optional<Integer> initialClientOrderSize
    ) {
        this.ingredientRepository = ingredientRepository;
        this.tacoRepository = tacoRepository;
        this.clientOrderRepository = clientOrderRepository;
        this.tacoBuilderFactory = tacoBuilderFactory;
        this.clientOrderBuilderFactory = clientOrderBuilderFactory;
        this.initialClientOrderSize = initialClientOrderSize;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        LOG.warn("Generate order on start up");

        IntStream.rangeClosed(1, initialClientOrderSize.orElse(1))
            .mapToObj(index -> {
                Taco taco1 = tacoBuilderFactory.create()
                    .name("Taco " + index + ".1")
                    .ingredients(generateIngredients())
                    .build();

                taco1 = tacoRepository.save(taco1);

                Taco taco2 = tacoBuilderFactory.create()
                    .name("Taco " + index + ".2")
                    .ingredients(generateIngredients())
                    .build();

                taco2 = tacoRepository.save(taco2);

                ClientOrder clientOrder = clientOrderBuilderFactory.create()
                    .deliveryName("Taco order " + index)
                    .deliveryStreet("Street " + index)
                    .deliveryState("State " + index)
                    .deliveryCity("City " + index)
                    .deliveryZip("5000")
                    .ccNumber("1234123412341234")
                    .ccExpiration("08/24")
                    .ccCVV("123")
                    .build();

                clientOrder.addTaco(taco1);
                clientOrder.addTaco(taco2);

                return clientOrder;
            })
            .forEach(clientOrderRepository::save);
    }

    private Collection<Ingredient> generateIngredients() {
        Set<Ingredient> allIngredients = new HashSet<>();

        for (IngredientType ingredientType : IngredientType.values()) {
            allIngredients.addAll(ingredientRepository.findByType(ingredientType));
        }

        return allIngredients.stream()
            .sorted(Comparator
                .comparing(o -> ((Ingredient) o).getType().name())
                .thenComparing(o -> ((Ingredient) o).getName())
            )
            .toList();
    }
}
