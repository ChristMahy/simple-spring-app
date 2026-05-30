package cmahy.simple.spring.webapp.resource.impl.adapter.startup.generator.taco;

import cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.ApplicationProperties;
import cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.start.OnStartProperties;
import cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.start.ResourcesProperties;
import cmahy.simple.spring.webapp.resource.impl.adapter.startup.generator.GeneratorConstants;
import cmahy.simple.spring.webapp.resource.impl.adapter.startup.generator.taco.vo.input.*;
import cmahy.simple.spring.webapp.taco.shop.kernel.application.repository.*;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.*;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.builder.ClientOrderBuilder;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.builder.factory.ClientOrderBuilderFactory;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.builder.factory.TacoBuilderFactory;
import cmahy.simple.spring.webapp.user.kernel.application.repository.UserSecurityRepository;
import cmahy.simple.spring.webapp.user.kernel.domain.UserSecurity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.*;

@Component
@Order(GeneratorConstants.TacoGeneratorExecutionOrder.CLIENT_ORDER)
public class ClientOrderGenerator implements ApplicationListener<ApplicationStartedEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(ClientOrderGenerator.class);

    private final IngredientRepository<Ingredient> ingredientRepository;
    private final TacoRepository<Taco> tacoRepository;
    private final ClientOrderRepository<ClientOrder> clientOrderRepository;
    private final UserSecurityRepository<UserSecurity> userSecurityRepository;
    private final TacoBuilderFactory<Taco> tacoBuilderFactory;
    private final ClientOrderBuilderFactory<ClientOrder> clientOrderBuilderFactory;
    private final ApplicationProperties applicationProperties;
    private final ObjectMapper objectMapper;

    public ClientOrderGenerator(
        IngredientRepository ingredientRepository,
        TacoRepository tacoRepository,
        ClientOrderRepository clientOrderRepository,
        UserSecurityRepository userSecurityRepository,
        TacoBuilderFactory tacoBuilderFactory,
        ClientOrderBuilderFactory clientOrderBuilderFactory,
        ApplicationProperties applicationProperties,
        ObjectMapper objectMapper
    ) {
        this.ingredientRepository = ingredientRepository;
        this.tacoRepository = tacoRepository;
        this.clientOrderRepository = clientOrderRepository;
        this.userSecurityRepository = userSecurityRepository;
        this.tacoBuilderFactory = tacoBuilderFactory;
        this.clientOrderBuilderFactory = clientOrderBuilderFactory;
        this.applicationProperties = applicationProperties;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ApplicationStartedEvent event) {

        try {
            Optional<Resource> clientsOrdersResource = Optional.ofNullable(applicationProperties.onStart())
                .map(OnStartProperties::resources)
                .map(ResourcesProperties::clientsOrders);

            if (clientsOrdersResource.map(Resource::exists).orElse(Boolean.FALSE)) {

                LOG.warn("Generate order on start up");

                try (InputStream clientsOrdersStream = clientsOrdersResource.get().getInputStream()) {

                    List<ClientOrderGeneratorInputVo> clientsOrdersInputs = objectMapper.readValue(
                        clientsOrdersStream,
                        new TypeReference<>() {
                        }
                    );

                    for (ClientOrderGeneratorInputVo clientOrderInput : clientsOrdersInputs) {

                        ClientOrderBuilder<ClientOrder> clientOrderBuilder = clientOrderBuilderFactory.create()
                            .deliveryName(clientOrderInput.deliveryName())
                            .deliveryStreet(clientOrderInput.deliveryStreet())
                            .deliveryState(clientOrderInput.deliveryState())
                            .deliveryCity(clientOrderInput.deliveryCity())
                            .deliveryZip(clientOrderInput.deliveryZip())
                            .ccNumber(clientOrderInput.ccNumber())
                            .ccExpiration(clientOrderInput.ccExpiration())
                            .ccCVV(clientOrderInput.ccCVV());

                        userSecurityRepository
                            .findByUserNameAndAuthProvider(
                                clientOrderInput.user().userName(), clientOrderInput.user().authProvider()
                            )
                            .ifPresent(clientOrderBuilder::user);

                        ClientOrder clientOrder = clientOrderBuilder.build();

                        for (TacoGeneratorInputVo tacoInput : clientOrderInput.tacos()) {

                            Collection<Ingredient> ingredients = map(tacoInput.ingredients());

                            Taco taco = tacoBuilderFactory.create()
                                .name(tacoInput.name())
                                .ingredients(ingredients)
                                .build();

                            taco = tacoRepository.save(taco);

                            LOG.info("<{}> saved <{}>", taco.getClass().getSimpleName(), taco);

                            clientOrder.addTaco(taco);

                        }

                        clientOrder = clientOrderRepository.save(clientOrder);

                        LOG.info("<{}> saved <{}>", clientOrder.getClass().getSimpleName(), clientOrder);

                    }

                }

            }

        } catch (Exception any) {
            throw new RuntimeException(any);
        }

    }

    private Collection<Ingredient> map(Collection<IngredientGeneratorInputVo> ingredientsInputs) {

        Set<Ingredient> ingredients = new HashSet<>();

        for (IngredientGeneratorInputVo ingredientInput : ingredientsInputs) {

            ingredientRepository
                .findByNameAndType(
                    ingredientInput.name(), ingredientInput.type()
                )
                .ifPresent(ingredients::add);

        }

        return ingredients.stream()
            .sorted(Comparator
                .comparing(o -> ((Ingredient) o).getType().name())
                .thenComparing(o -> ((Ingredient) o).getName())
            )
            .toList();

    }
}
