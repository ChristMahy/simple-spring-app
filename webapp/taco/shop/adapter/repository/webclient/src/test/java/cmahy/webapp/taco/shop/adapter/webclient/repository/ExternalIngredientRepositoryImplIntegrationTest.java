package cmahy.webapp.taco.shop.adapter.webclient.repository;

import cmahy.common.entity.page.DefaultEntityPageableImpl;
import cmahy.common.helper.Generator;
import cmahy.common.json.JsonMapperFactory;
import cmahy.webapp.taco.shop.adapter.webclient.entity.domain.ExternalIngredient;
import cmahy.webapp.taco.shop.adapter.webclient.exception.IngredientExternalResourceException;
import cmahy.webapp.taco.shop.adapter.webclient.util.integration.test.BackEndStub;
import cmahy.webapp.taco.shop.adapter.webclient.util.integration.test.TestWebClientApplicationStub;
import cmahy.webapp.taco.shop.kernel.domain.IngredientType;
import cmahy.webapp.taco.shop.kernel.domain.page.IngredientPage;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;

import java.util.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = {TestWebClientApplicationStub.class})
class ExternalIngredientRepositoryImplIntegrationTest {

    private static ObjectMapper jsonMapper = JsonMapperFactory.create();

    @Autowired
    private ExternalIngredientRepositoryImpl externalIngredientRepository;

    @BeforeAll
    static void beforeAll() {
        assertDoesNotThrow(() -> {
            BackEndStub.INSTANCE.getMockBackEnd().start();
        });
    }

    @AfterAll
    static void afterAll() {
        assertDoesNotThrow(() -> {
            BackEndStub.INSTANCE.getMockBackEnd().shutdown();
        });
    }

    @BeforeEach
    void setUp() {
        assertDoesNotThrow(() -> {
        });
    }

    @Test
    void findAll() {
        assertDoesNotThrow(() -> {
            List<ExternalIngredient> ingredients = Stream
                .generate(() -> new ExternalIngredient()
                    .setId(Generator.randomUUID())
                    .setName(Generator.generateAString())
                    .setType(Generator.randomEnum(IngredientType.class))
                )
                .limit(Generator.randomInt(50, 500))
                .toList();

            IngredientPage<ExternalIngredient> ingredientPage = new IngredientPage<>(
                ingredients,
                Generator.randomLong(Integer.valueOf(ingredients.size()).longValue(), 100000L)
            );

            BackEndStub.INSTANCE.getMockBackEnd().enqueue(
                new MockResponse()
                    .setBody(jsonMapper.writeValueAsString(ingredientPage))
                    .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            );

            IngredientPage<ExternalIngredient> actual = externalIngredientRepository.findAll(new DefaultEntityPageableImpl(
                0,
                ingredients.size()
            ));

            assertThat(actual)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(ingredientPage);
        });
    }

    @ParameterizedTest
    @MethodSource("onlyErrors")
    void findAll_onUnexpectedError_thenReturnEmptyIngredientPage(ProblemDetail error) {
        assertThrows(IngredientExternalResourceException.class, () -> {
            BackEndStub.INSTANCE.getMockBackEnd().enqueue(
                new MockResponse()
                    .setResponseCode(error.getStatus())
                    .setBody(jsonMapper.writeValueAsString(error))
                    .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PROBLEM_JSON_VALUE)
            );

            externalIngredientRepository.findAll(new DefaultEntityPageableImpl(
                Generator.randomIntEqualOrAboveZero(),
                Generator.randomIntEqualOrAboveZero()
            ));
        });
    }

    private static Stream<ProblemDetail> onlyErrors() {
        return Arrays.stream(HttpStatus.values())
            .filter(status -> (status.is4xxClientError() && !status.isSameCodeAs(HttpStatus.NOT_FOUND)) || status.is5xxServerError())
            .map(status -> ProblemDetail.forStatusAndDetail(status, status.name()));
    }

    @Test
    void findAll_onNotFound_thenReturnEmptyIngredientPage() {
        assertDoesNotThrow(() -> {
            BackEndStub.INSTANCE.getMockBackEnd().enqueue(
                new MockResponse()
                    .setResponseCode(404)
                    .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            );

            IngredientPage<ExternalIngredient> actual = externalIngredientRepository.findAll(new DefaultEntityPageableImpl(
                Generator.randomIntEqualOrAboveZero(),
                Generator.randomIntEqualOrAboveZero()
            ));

            assertThat(actual).isNotNull();

            assertThat(actual.content()).isNotNull().hasSize(0);
            assertThat(actual.totalElements()).isNotNull().isEqualTo(0L);
        });
    }
}