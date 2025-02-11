package cmahy.webapp.resource.impl.adapter.taco.shop.scheduler;

import cmahy.common.entity.page.EntityPageable;
import cmahy.common.helper.Generator;
import cmahy.webapp.taco.shop.kernel.application.query.GetAllRemoteIngredientPagedQuery;
import cmahy.webapp.taco.shop.kernel.domain.IngredientType;
import cmahy.webapp.taco.shop.kernel.domain.id.IngredientId;
import cmahy.webapp.taco.shop.kernel.vo.output.IngredientOutputVo;
import cmahy.webapp.taco.shop.kernel.vo.output.IngredientPageOutputVo;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientSchedulerTest {

    @Mock
    private GetAllRemoteIngredientPagedQuery getAllRemoteIngredientQuery;

    @InjectMocks
    private IngredientScheduler ingredientScheduler;

    @Test
    void runGetAllQuery() {
        assertDoesNotThrow(() -> {
            List<IngredientOutputVo> ingredients = Stream
                .generate(() -> new IngredientOutputVo(
                    new IngredientId(Generator.randomUUID()),
                    Generator.generateAString(),
                    Generator.randomEnum(IngredientType.class).name()
                ))
                .limit(Generator.randomInt(50, 1000))
                .toList();

            IngredientPageOutputVo page = new IngredientPageOutputVo(
                ingredients,
                Integer.valueOf(ingredients.size()).longValue()
            );

            when(getAllRemoteIngredientQuery.execute(any(EntityPageable.class))).thenReturn(page);

            ingredientScheduler.runGetAllQuery();
        });
    }

    @Test
    void runGetAllQuery_whenResultIsNull_thenSkip() {
        assertDoesNotThrow(() -> {
            when(getAllRemoteIngredientQuery.execute(any(EntityPageable.class))).thenReturn(null);

            ingredientScheduler.runGetAllQuery();
        });
    }

    @ParameterizedTest()
    @MethodSource({ "exceptions" })
    void runGetAllQuery_isRunningThreadSafe(Throwable exception) {
        assertDoesNotThrow(() -> {

            when(getAllRemoteIngredientQuery.execute(any(EntityPageable.class))).thenAnswer(_ -> { throw exception; });

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