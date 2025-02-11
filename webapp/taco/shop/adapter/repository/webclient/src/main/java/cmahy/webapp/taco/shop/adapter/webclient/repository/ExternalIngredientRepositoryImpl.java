package cmahy.webapp.taco.shop.adapter.webclient.repository;

import cmahy.common.entity.page.EntityPageable;
import cmahy.webapp.taco.shop.adapter.webclient.entity.domain.ExternalIngredient;
import cmahy.webapp.taco.shop.adapter.webclient.exception.IngredientExternalResourceException;
import cmahy.webapp.taco.shop.kernel.application.repository.IngredientPagingRepository;
import cmahy.webapp.taco.shop.kernel.application.repository.IngredientRepository;
import cmahy.webapp.taco.shop.kernel.application.repository.annotation.RemoteRepository;
import cmahy.webapp.taco.shop.kernel.domain.IngredientType;
import cmahy.webapp.taco.shop.kernel.domain.id.IngredientId;
import cmahy.webapp.taco.shop.kernel.domain.page.IngredientPage;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.*;

@Named
@RemoteRepository
public class ExternalIngredientRepositoryImpl implements IngredientRepository<ExternalIngredient>, IngredientPagingRepository<ExternalIngredient> {

    private static final Logger LOG = LoggerFactory.getLogger(ExternalIngredientRepositoryImpl.class);

    private final WebClient tacoResource;

    public ExternalIngredientRepositoryImpl(
        WebClient tacoResource
    ) {
        this.tacoResource = tacoResource;
    }

    @Override
    public Optional<ExternalIngredient> findById(IngredientId id) {
        throw new IllegalStateException("Not yet implemented !");
    }

    @Override
    public Set<ExternalIngredient> findByType(IngredientType type) {
        throw new IllegalStateException("Not yet implemented !");
    }

    @Override
    public Optional<ExternalIngredient> findByNameAndType(String name, IngredientType type) {
        throw new IllegalStateException("Not yet implemented !");
    }

    @Override
    public ExternalIngredient save(ExternalIngredient ingredient) {
        throw new IllegalStateException("Not yet implemented !");
    }

    @Override
    public void deleteById(IngredientId id) {
        throw new IllegalStateException("Not yet implemented !");
    }

    @Override
    public IngredientPage<ExternalIngredient> findAll(EntityPageable pageable) {
        LOG.info("Process remote fetching ingredients");

        try {
            return tacoResource.get()
                .uri(
                    "/ingredient",
                    uriBuilder -> uriBuilder
                        .queryParam("page-number", "{pageNumber}")
                        .queryParam("page-size", "{pageSize}")
                        .build(pageable.pageNumber(), pageable.pageSize())
                )
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(StandardCharsets.UTF_8)
                .ifModifiedSince(ZonedDateTime.now())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<IngredientPage<ExternalIngredient>>() {})
                .block();
        } catch (WebClientResponseException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return new IngredientPage<>(Collections.emptyList(), 0L);
            }

            LOG.error("Remote fetching ingredients failed", e);

            throw new IngredientExternalResourceException("Remote fetch all failed", e);
        } catch (Exception others) {
            LOG.error("Remote fetching ingredients failed", others);

            throw new IngredientExternalResourceException("Remote fetch all failed", others);
        }
    }
}
