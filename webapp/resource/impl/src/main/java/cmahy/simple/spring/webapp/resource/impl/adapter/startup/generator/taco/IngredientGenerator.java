package cmahy.simple.spring.webapp.resource.impl.adapter.startup.generator.taco;

import cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.ApplicationProperties;
import cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.start.OnStartProperties;
import cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.start.ResourcesProperties;
import cmahy.simple.spring.webapp.resource.impl.adapter.startup.generator.GeneratorConstants;
import cmahy.simple.spring.webapp.resource.impl.adapter.startup.generator.taco.vo.input.IngredientGeneratorInputVo;
import cmahy.simple.spring.webapp.taco.shop.kernel.application.command.CreateIngredientCommand;
import cmahy.simple.spring.webapp.taco.shop.kernel.application.repository.IngredientRepository;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.simple.spring.webapp.taco.shop.kernel.vo.input.IngredientCreateInputVo;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Component
@Order(GeneratorConstants.TacoGeneratorExecutionOrder.INGREDIENT)
public class IngredientGenerator implements ApplicationListener<ApplicationStartedEvent> {

    private final CreateIngredientCommand createIngredientCommand;
    private final IngredientRepository<? extends Ingredient> ingredientRepository;
    private final ApplicationProperties applicationProperties;
    private final ObjectMapper objectMapper;

    public IngredientGenerator(
        CreateIngredientCommand createIngredientCommand,
        IngredientRepository ingredientRepository,
        ApplicationProperties applicationProperties,
        ObjectMapper objectMapper
    ) {
        this.createIngredientCommand = createIngredientCommand;
        this.ingredientRepository = ingredientRepository;
        this.applicationProperties = applicationProperties;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ApplicationStartedEvent event) {

        try {

            Optional<Resource> ingredientsResource = Optional.ofNullable(applicationProperties.onStart())
                .map(OnStartProperties::resources)
                .map(ResourcesProperties::ingredients);

            if (ingredientsResource.map(Resource::exists).orElse(Boolean.FALSE)) {

                try (InputStream ingredientsStream = ingredientsResource.get().getInputStream()) {

                    List<IngredientGeneratorInputVo> ingredientsInputs = objectMapper.readValue(
                        ingredientsStream,
                        new TypeReference<>() {
                        }
                    );

                    for (IngredientGeneratorInputVo ingredientInput : ingredientsInputs) {

                        if (this.ingredientDoesNotExists(ingredientInput)) {

                            createIngredientCommand.execute(new IngredientCreateInputVo(
                                ingredientInput.name(), ingredientInput.type().name()
                            ));

                        }

                    }

                }

            }

        } catch (Exception any) {
            throw new RuntimeException(any);
        }

    }

    private boolean ingredientDoesNotExists(IngredientGeneratorInputVo ingredient) {
        return ingredientRepository
            .findByNameAndType(ingredient.name(), ingredient.type())
            .isEmpty();
    }
}
