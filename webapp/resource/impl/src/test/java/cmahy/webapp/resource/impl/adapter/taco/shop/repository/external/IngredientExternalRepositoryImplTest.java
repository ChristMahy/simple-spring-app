package cmahy.webapp.resource.impl.adapter.taco.shop.repository.external;

import cmahy.common.helper.Generator;
import cmahy.webapp.resource.impl.domain.taco.external.page.IngredientExternalPage;
import cmahy.webapp.resource.impl.domain.taco.page.IngredientPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Set;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IngredientExternalRepositoryImplTest {

    @Mock
    private WebClient tacoResource;

    @InjectMocks
    private IngredientExternalRepositoryImpl ingredientExternalRepository;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @BeforeEach
    void setUp() {
        assertDoesNotThrow(() -> {
            when(tacoResource.get()).thenReturn(requestHeadersUriSpec);
            when(requestHeadersUriSpec.uri(any(Function.class))).thenReturn(requestHeadersUriSpec);
            when(requestHeadersUriSpec.accept(MediaType.APPLICATION_JSON)).thenReturn(requestHeadersUriSpec);
            when(requestHeadersUriSpec.acceptCharset(StandardCharsets.UTF_8)).thenReturn(requestHeadersUriSpec);
            when(requestHeadersUriSpec.ifModifiedSince(any(ZonedDateTime.class))).thenReturn(requestHeadersUriSpec);
            when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
        });
    }

    @Test
    void findAllIngredients() {
        assertDoesNotThrow(() -> {
            IngredientExternalPage page = mock(IngredientExternalPage.class);

            when(responseSpec.bodyToMono(IngredientExternalPage.class)).thenReturn(Mono.just(page));

            IngredientExternalPage actual = ingredientExternalRepository.findAllIngredients();

            assertThat(actual)
                .isNotNull()
                .isEqualTo(page);
        });
    }

    @ParameterizedTest
    @MethodSource("exceptions")
    void findAllIngredients_onAnyError_thenReturnEmptyPage(Throwable throwable) {
        assertDoesNotThrow(() -> {
            when(responseSpec.bodyToMono(IngredientExternalPage.class)).thenReturn(Mono.error(throwable));

            IngredientExternalPage actual = ingredientExternalRepository.findAllIngredients();

            assertThat(actual).isNotNull();

            assertThat(actual.content()).isEmpty();
            assertThat(actual.totalElements()).isEqualTo(0);
        });
    }

    public static Set<Throwable> exceptions() {
        return Set.of(
            new Throwable(Generator.generateAString()),
            new Exception(Generator.generateAString()),
            new RuntimeException(Generator.generateAString()),
            new IllegalArgumentException(Generator.generateAString()),
            new IllegalStateException(Generator.generateAString())
        );
    }
}