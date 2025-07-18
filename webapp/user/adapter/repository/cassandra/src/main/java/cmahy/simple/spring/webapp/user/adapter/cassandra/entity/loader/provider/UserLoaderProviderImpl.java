package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.loader.provider;

import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.loader.Loader;
import jakarta.inject.Named;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.*;
import java.util.stream.Collectors;

@Named
@ConditionalOnMissingBean(UserLoaderProvider.class)
public class UserLoaderProviderImpl implements UserLoaderProvider {

    private final Map<Class<?>, Loader> loaders;

    public UserLoaderProviderImpl(Collection<Loader> loaders) {
        Collection<Loader> expectedAnnotationMissing = loaders.stream()
            .filter(loader -> !loader.entityClass().isAnnotationPresent(Table.class))
            .toList();

        if (!expectedAnnotationMissing.isEmpty()) {
            String loadersMissingAnnotation = expectedAnnotationMissing.stream()
                .map(loader -> loader.getClass().getName())
                .collect(Collectors.joining(", "));

            throw new IllegalArgumentException(
                "Only loaders which handle Cassandra entity are allowed <" + loadersMissingAnnotation + ">"
            );
        }

        this.loaders = loaders.stream()
            .collect(Collectors.toMap(Loader::entityClass, loader -> loader));
    }

    @Override
    public <T, L extends Loader> L resolve(Class<T> entityClass) {
        return Optional.ofNullable(entityClass)
            .filter(loaders::containsKey)
            .map(ec -> (L) loaders.get(ec))
            .orElseThrow(() -> new IllegalArgumentException("Entity class not match"));
    }

}
