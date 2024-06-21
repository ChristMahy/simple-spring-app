package cmahy.webapp.resource.impl.adapter.taco.shop.scheduler;

import cmahy.common.helper.Generator;
import cmahy.webapp.resource.impl.application.taco.external.query.GetAllExternalIngredientQuery;
import cmahy.webapp.resource.impl.domain.taco.IngredientType;
import cmahy.webapp.resource.impl.domain.taco.external.IngredientExternal;
import cmahy.webapp.resource.impl.domain.taco.external.page.IngredientExternalPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IngredientSchedulerTest {

    @Mock
    private GetAllExternalIngredientQuery getAllExternalIngredientQuery;

    @InjectMocks
    private IngredientScheduler ingredientScheduler;

    @Test
    void runGetAllQuery() {
        assertDoesNotThrow(() -> {
            List<IngredientExternal> ingredients = Stream
                .generate(() -> {
                    IngredientExternal ingredient = new IngredientExternal(
                        Generator.generateAString(),
                        Generator.generateAString(),
                        Generator.randomEnum(IngredientType.class)
                        );

                    return ingredient;
                })
                .limit(Generator.randomInt(50, 1000))
                .toList();

            IngredientExternalPage page = new IngredientExternalPage(
                ingredients,
                Integer.valueOf(ingredients.size()).longValue()
            );

            when(getAllExternalIngredientQuery.execute()).thenReturn(page);

            ingredientScheduler.runGetAllQuery();
        });
    }

    @ParameterizedTest()
    @MethodSource({ "exceptions" })
    void runGetAllQuery_isRunningThreadSafe(Throwable exception) {
        assertDoesNotThrow(() -> {
            when(getAllExternalIngredientQuery.execute()).thenAnswer(invocationOnMock -> { throw exception; });

            ingredientScheduler.runGetAllQuery();
        });
    }

    private static Set<Throwable> exceptions() {
        return Set.of(
            new Throwable(Generator.generateAString()),
            new Exception(Generator.generateAString()),
            new RuntimeException(Generator.generateAString()),
            new IllegalArgumentException(Generator.generateAString()),
            new IllegalStateException(Generator.generateAString()),
            new IOException(Generator.generateAString())
        );
    }
}