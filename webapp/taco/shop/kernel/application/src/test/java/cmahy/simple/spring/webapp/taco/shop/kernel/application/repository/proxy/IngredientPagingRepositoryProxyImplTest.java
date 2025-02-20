package cmahy.simple.spring.webapp.taco.shop.kernel.application.repository.proxy;

import cmahy.simple.spring.common.entity.page.EntityPageable;
import cmahy.simple.spring.webapp.taco.shop.kernel.application.repository.IngredientPagingRepository;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.page.IngredientPage;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

class IngredientPagingRepositoryProxyImplTest {

    @Test
    void findAll_whenExternalIsPresent_thenUseExternal() {
        assertDoesNotThrow(() -> {
            IngredientPagingRepository<Ingredient> defaultIngredientPagingRepository = mock(IngredientPagingRepository.class);
            IngredientPagingRepository<Ingredient> externalIngredientRepositoryImpl = mock(IngredientPagingRepository.class);

            EntityPageable pageable = mock(EntityPageable.class);
            IngredientPage<Ingredient> page = mock(IngredientPage.class);

            IngredientPagingRepositoryProxyImpl ingredientPagingRepositoryProxy = new IngredientPagingRepositoryProxyImpl(
                defaultIngredientPagingRepository,
                Optional.of(externalIngredientRepositoryImpl)
            );

            when(externalIngredientRepositoryImpl.findAll(pageable)).thenReturn(page);

            IngredientPage<Ingredient> actual = ingredientPagingRepositoryProxy.findAll(pageable);

            assertThat(actual)
                .isNotNull()
                .isSameAs(page);

            verifyNoInteractions(defaultIngredientPagingRepository);
        });
    }

    @Test
    void findAll_whenExternalIsNotPresent_thenUseDefault() {
        assertDoesNotThrow(() -> {
            IngredientPagingRepository<Ingredient> defaultIngredientPagingRepository = mock(IngredientPagingRepository.class);

            EntityPageable pageable = mock(EntityPageable.class);
            IngredientPage<Ingredient> page = mock(IngredientPage.class);

            IngredientPagingRepositoryProxyImpl ingredientPagingRepositoryProxy = new IngredientPagingRepositoryProxyImpl(
                defaultIngredientPagingRepository,
                Optional.empty()
            );

            when(defaultIngredientPagingRepository.findAll(pageable)).thenReturn(page);

            IngredientPage<Ingredient> actual = ingredientPagingRepositoryProxy.findAll(pageable);

            assertThat(actual)
                .isNotNull()
                .isSameAs(page);
        });
    }
}