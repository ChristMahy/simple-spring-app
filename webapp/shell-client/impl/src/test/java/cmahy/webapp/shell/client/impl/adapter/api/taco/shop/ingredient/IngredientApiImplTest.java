package cmahy.webapp.shell.client.impl.adapter.api.taco.shop.ingredient;

import cmahy.common.entity.page.DefaultEntityPageableImpl;
import cmahy.common.entity.page.EntityPageable;
import cmahy.common.helper.Generator;
import cmahy.webapp.shell.client.impl.adapter.config.properties.ingredient.IngredientProperties;
import cmahy.webapp.shell.client.impl.adapter.config.properties.ingredient.IngredientResource;
import cmahy.webapp.shell.client.impl.application.query.PrintMessageQuery;
import cmahy.webapp.shell.client.impl.application.repository.property.ConsolePropertyRepository;
import cmahy.webapp.taco.shop.kernel.application.query.GetAllRemoteIngredientPagedQuery;
import cmahy.webapp.taco.shop.kernel.domain.IngredientType;
import cmahy.webapp.taco.shop.kernel.domain.id.IngredientId;
import cmahy.webapp.taco.shop.kernel.vo.output.IngredientOutputVo;
import cmahy.webapp.taco.shop.kernel.vo.output.IngredientPageOutputVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import picocli.CommandLine;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.apache.commons.lang3.StringEscapeUtils.escapeJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientApiImplTest {

    @Mock
    private PrintMessageQuery printMessageQuery;

    @Mock
    private ConsolePropertyRepository consolePropertyRepository;

    @Mock
    private GetAllRemoteIngredientPagedQuery getAllIngredientPagedQuery;

    @Mock
    private IngredientProperties ingredientProperties;

    @InjectMocks
    private IngredientApiImpl ingredientApiImpl;

    @Test
    void call() {
        assertDoesNotThrow(() -> {
            try (MockedStatic<CommandLine> ignored = mockStatic(CommandLine.class)) {
                when(consolePropertyRepository.findFormat()).thenReturn(Optional.empty());

                Integer actual = ingredientApiImpl.call();

                assertThat(actual).isEqualTo(0);
            }
        });
    }

    @ParameterizedTest
    @MethodSource("aBunchOfException")
    void call_onAnyException_shouldBeThreadSafeAndReturnOne(Exception anException) {
        assertDoesNotThrow(() -> {
            try (MockedStatic<CommandLine> ignored = mockStatic(CommandLine.class)) {
                when(consolePropertyRepository.findFormat()).thenAnswer(_ -> {
                    throw anException;
                });

                Integer actual = ingredientApiImpl.call();

                assertThat(actual).isEqualTo(1);
            }
        });
    }

    @Test
    void getAll() {
        assertDoesNotThrow(() -> {
            IngredientResource ingredientResource = mock(IngredientResource.class);
            Integer pageSize = Generator.randomInt(10, 50);

            Long totalElements = Generator.randomLong(50L, 500L);

            IngredientOutputVo ingredientOutputVo = mock(IngredientOutputVo.class);

            UUID id = Generator.randomUUID();
            String name = Generator.generateAString();
            IngredientType type = Generator.randomEnum(IngredientType.class);

            when(ingredientOutputVo.id()).thenReturn(new IngredientId(id));
            when(ingredientOutputVo.name()).thenReturn(name);
            when(ingredientOutputVo.type()).thenReturn(type.name());

            when(ingredientOutputVo.toString()).thenCallRealMethod();

            try (MockedStatic<CommandLine> ignored = mockStatic(CommandLine.class)) {
                when(ingredientProperties.externalResource()).thenReturn(ingredientResource);
                when(ingredientResource.pageSize()).thenReturn(Optional.of(pageSize));

                when(getAllIngredientPagedQuery.execute(any(EntityPageable.class))).thenAnswer(invocationOnMock -> {
                    DefaultEntityPageableImpl pagination = invocationOnMock.getArgument(0, DefaultEntityPageableImpl.class);

                    IngredientPageOutputVo ingredientPageOutputVo = mock(IngredientPageOutputVo.class);

                    long limit = ((long) (pagination.pageNumber() + 1) * pagination.pageSize()) < totalElements ? pageSize : (totalElements - ((long) pagination.pageNumber() * pagination.pageSize()));

                    List<IngredientOutputVo> elements = Stream
                        .generate(() -> ingredientOutputVo)
                        .limit(limit)
                        .toList();

                    when(ingredientPageOutputVo.content()).thenReturn(elements);
                    when(ingredientPageOutputVo.totalElements()).thenReturn(totalElements);

                    return ingredientPageOutputVo;
                });

                ingredientApiImpl.getAll();
            }

            verify(printMessageQuery, times(totalElements.intValue()))
                .execute(ArgumentMatchers.matches(Pattern.compile(
                    "^(?=.*" + Pattern.quote(escapeJson(id.toString())) + ")(?=.*" + Pattern.quote(escapeJson(name)) + ")(?=.*" + Pattern.quote(escapeJson(type.name())) + ").*$"
                )));
        });
    }

    @ParameterizedTest
    @MethodSource("aBunchOfException")
    void getAll_onAnyException_shouldBeThreadSafeAndReturnOne(Exception anException) {
        assertDoesNotThrow(() -> {
            try (MockedStatic<CommandLine> ignored = mockStatic(CommandLine.class)) {
                when(ingredientProperties.externalResource()).thenAnswer(_ -> {
                    throw anException;
                });

                assertThat(ingredientApiImpl.getAll()).isEqualTo(1);
            }
        });
    }

    protected static Stream<Exception> aBunchOfException() {
        return Stream.of(
            new Exception(Generator.generateAString()),
            new RuntimeException(Generator.generateAString()),
            new IllegalStateException(Generator.generateAString()),
            new IllegalArgumentException(Generator.generateAString()),
            new NullPointerException(Generator.generateAString())
        );
    }
}