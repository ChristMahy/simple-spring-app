package cmahy.webapp.taco.shop.adapter.webclient.repository;

import cmahy.common.entity.page.EntityPageable;
import cmahy.common.helper.Generator;
import cmahy.webapp.taco.shop.adapter.webclient.entity.ExternalIngredient;
import cmahy.webapp.taco.shop.adapter.webclient.entity.ExternalTaco;
import cmahy.webapp.taco.shop.adapter.webclient.exception.IngredientExternalResourceException;
import cmahy.webapp.taco.shop.kernel.domain.IngredientType;
import cmahy.webapp.taco.shop.kernel.domain.id.IngredientId;
import cmahy.webapp.taco.shop.kernel.domain.page.IngredientPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Set;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExternalIngredientRepositoryImplTest {

    @Mock
    private WebClient tacoResource;

    @InjectMocks
    private ExternalIngredientRepositoryImpl externalIngredientRepositoryImpl;

    @Test
    void findById() {
        assertThrows(IllegalStateException.class, () -> {
            externalIngredientRepositoryImpl.findById(mock(IngredientId.class));
        });
    }

    @Test
    void findByType() {
        assertThrows(IllegalStateException.class, () -> {
            externalIngredientRepositoryImpl.findByType(mock(IngredientType.class));
        });
    }

    @Test
    void findByNameAndType() {
        assertThrows(IllegalStateException.class, () -> {
            externalIngredientRepositoryImpl.findByNameAndType(Generator.generateAString(), mock(IngredientType.class));
        });
    }

    @Test
    void save() {
        assertThrows(IllegalStateException.class, () -> {
            externalIngredientRepositoryImpl.save(mock(ExternalIngredient.class));
        });
    }

    @Test
    void deleteById() {
        assertThrows(IllegalStateException.class, () -> {
            externalIngredientRepositoryImpl.deleteById(mock(IngredientId.class));
        });
    }

    @Test
    void findAll() {
        assertDoesNotThrow(() -> {
            EntityPageable entityPageable = mock(EntityPageable.class);

            WebClient.RequestHeadersUriSpec requestHeadersUriSpec = mock(WebClient.RequestHeadersUriSpec.class, RETURNS_SELF);

            UriBuilder uriBuilder = mock(UriBuilder.class, RETURNS_SELF);
            URI uri = mock(URI.class);
            when(uriBuilder.build(any(), any())).thenReturn(uri);

            when(requestHeadersUriSpec.uri(anyString(), any(Function.class))).thenAnswer(invocationOnMock -> {
                Object[] arguments = invocationOnMock.getArguments();

                assertThat(arguments.length).isEqualTo(2);

                Function<UriBuilder, URI> uriBuilderFn = (Function<UriBuilder, URI>) arguments[1];

                URI actualUri = uriBuilderFn.apply(uriBuilder);

                assertThat(actualUri).isNotNull().isSameAs(uri);

                return invocationOnMock.getMock();
            });

            WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);
            when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);

            IngredientPage<ExternalIngredient> ingredientPage = mock(IngredientPage.class);

            Mono<IngredientPage<ExternalIngredient>> ingredientPageMono = Mono.just(ingredientPage);
            when(responseSpec.bodyToMono(any(ParameterizedTypeReference.class))).thenReturn(ingredientPageMono);

            when(tacoResource.get()).thenReturn(requestHeadersUriSpec);

            IngredientPage<ExternalIngredient> actual = externalIngredientRepositoryImpl.findAll(entityPageable);

            assertThat(actual)
                .isNotNull()
                .isSameAs(ingredientPage);

            verify(uriBuilder, times(2)).queryParam(anyString(), anyString());
        });
    }

    @Test
    void findAll_whenNotFound_thenReturnEmpty() {
        assertDoesNotThrow(() -> {
            EntityPageable entityPageable = mock(EntityPageable.class);

            WebClient.RequestHeadersUriSpec requestHeadersUriSpec = mock(WebClient.RequestHeadersUriSpec.class, RETURNS_SELF);

            WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);
            when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);

            when(responseSpec.bodyToMono(any(ParameterizedTypeReference.class))).thenReturn(Mono.error(new WebClientResponseException(
                HttpStatus.NOT_FOUND.value(),
                Generator.generateAString(),
                null, null, null
            )));

            when(tacoResource.get()).thenReturn(requestHeadersUriSpec);

            IngredientPage<ExternalIngredient> actual = externalIngredientRepositoryImpl.findAll(entityPageable);

            assertThat(actual).isNotNull();

            assertThat(actual.content()).isEmpty();
            assertThat(actual.totalElements()).isEqualTo(0);
        });
    }

    @ParameterizedTest
    @MethodSource("exceptions")
    void findAll_onAnyError_thenThrowIngredientExternalResourceException(Throwable throwable) {
        assertThrows(IngredientExternalResourceException.class, () -> {
            EntityPageable entityPageable = mock(EntityPageable.class);

            WebClient.RequestHeadersUriSpec requestHeadersUriSpec = mock(WebClient.RequestHeadersUriSpec.class, RETURNS_SELF);

            WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);
            when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);

            when(responseSpec.bodyToMono(any(ParameterizedTypeReference.class))).thenReturn(Mono.error(throwable));

            when(tacoResource.get()).thenReturn(requestHeadersUriSpec);

            externalIngredientRepositoryImpl.findAll(entityPageable);
        });
    }

    public static Set<Throwable> exceptions() {
        return Set.of(
            new Throwable(Generator.generateAString()),
            new Exception(Generator.generateAString()),
            new RuntimeException(Generator.generateAString()),
            new IllegalArgumentException(Generator.generateAString()),
            new IllegalStateException(Generator.generateAString()),
            new WebClientResponseException(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                Generator.generateAString(),
                null, null, null
            )
        );
    }
}