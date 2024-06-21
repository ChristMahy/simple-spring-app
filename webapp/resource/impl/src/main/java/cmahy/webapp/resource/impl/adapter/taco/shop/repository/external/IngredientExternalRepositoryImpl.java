package cmahy.webapp.resource.impl.adapter.taco.shop.repository.external;

import cmahy.webapp.resource.impl.application.taco.external.repository.IngredientExternalRepository;
import cmahy.webapp.resource.impl.domain.taco.external.page.IngredientExternalPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Collections;

@Component
public class IngredientExternalRepositoryImpl implements IngredientExternalRepository {

    private static final Logger LOG = LoggerFactory.getLogger(IngredientExternalRepositoryImpl.class);

    private final WebClient tacoResource;

    public IngredientExternalRepositoryImpl(WebClient tacoResource) {
        this.tacoResource = tacoResource;
    }

    public IngredientExternalPage findAllIngredients() {
        return tacoResource.get()
            .uri(uriBuilder -> uriBuilder
                .path("/ingredient")
                .queryParam("page-number", "{pageNumber}")
                .queryParam("page-size", "{pageSize}")
                .build(0, Integer.MAX_VALUE)
            )
            .accept(MediaType.APPLICATION_JSON)
            .acceptCharset(StandardCharsets.UTF_8)
            .ifModifiedSince(ZonedDateTime.now())
            .retrieve()
            .bodyToMono(IngredientExternalPage.class)
            .onErrorResume(e -> {
                LOG.error(e.getMessage(), e);

                return Mono.just(new IngredientExternalPage(Collections.emptyList(), 0L));
            })
            .block();
    }
}
