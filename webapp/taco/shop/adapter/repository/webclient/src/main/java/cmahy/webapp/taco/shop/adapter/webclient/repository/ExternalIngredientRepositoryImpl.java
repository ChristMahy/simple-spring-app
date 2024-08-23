package cmahy.webapp.taco.shop.adapter.webclient.repository;

import cmahy.common.entity.page.EntityPageable;
import cmahy.webapp.taco.shop.kernel.application.repository.IngredientPagingRepository;
import cmahy.webapp.taco.shop.kernel.application.repository.IngredientRepository;
import cmahy.webapp.taco.shop.kernel.application.repository.annotation.RemoteRepository;
import cmahy.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.webapp.taco.shop.kernel.domain.IngredientType;
import cmahy.webapp.taco.shop.kernel.domain.id.IngredientId;
import cmahy.webapp.taco.shop.kernel.domain.page.IngredientPage;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.*;

@Named
@RemoteRepository
public class ExternalIngredientRepositoryImpl implements IngredientRepository, IngredientPagingRepository {

    private static final Logger LOG = LoggerFactory.getLogger(ExternalIngredientRepositoryImpl.class);

    private final WebClient tacoResource;

    public ExternalIngredientRepositoryImpl(
        WebClient tacoResource
    ) {
        this.tacoResource = tacoResource;
    }

    @Override
    public Optional<Ingredient> findById(IngredientId id) {
        throw new IllegalStateException("Not yet implemented !");
    }

    @Override
    public Set<Ingredient> findByType(IngredientType type) {
        throw new IllegalStateException("Not yet implemented !");
    }

    @Override
    public Optional<Ingredient> findByNameAndType(String name, IngredientType type) {
        throw new IllegalStateException("Not yet implemented !");
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        throw new IllegalStateException("Not yet implemented !");
    }

    @Override
    public void deleteById(IngredientId id) {
        throw new IllegalStateException("Not yet implemented !");
    }

    @Override
    public IngredientPage findAll(EntityPageable pageable) {
        LOG.info("Process remote fetching ingredients");

        return tacoResource.get()
            .uri(uriBuilder -> uriBuilder
                .path("/ingredient")
                .queryParam("page-number", "{pageNumber}")
                .queryParam("page-size", "{pageSize}")
                .build(pageable.pageNumber(), pageable.pageSize())
            )
            .accept(MediaType.APPLICATION_JSON)
            .acceptCharset(StandardCharsets.UTF_8)
            .ifModifiedSince(ZonedDateTime.now())
            .retrieve()
            .bodyToMono(IngredientPage.class)
            .onErrorResume(e -> {
                LOG.error(e.getMessage(), e);

                return Mono.just(new IngredientPage(Collections.emptyList(), 0L));
            })
            .block();
    }
}
