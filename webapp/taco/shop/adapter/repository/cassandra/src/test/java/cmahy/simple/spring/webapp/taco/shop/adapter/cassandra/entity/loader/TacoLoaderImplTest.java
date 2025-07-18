package cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.loader;

import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.domain.CassandraIngredient;
import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.repository.cassandra.CassandraIngredientRepository;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.id.IngredientId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TacoLoaderImplTest {

    @Mock
    private CassandraIngredientRepository ingredientRepository;

    @InjectMocks
    private TacoLoaderImpl tacoLoaderImpl;

    @Test
    void loadIngredients() {
        assertDoesNotThrow(() -> {
            Set<IngredientId> ingredientIds = mock(Set.class);
            List<CassandraIngredient> ingredients = mock(List.class);

            when(ingredientRepository.findAllById(ingredientIds)).thenReturn(ingredients);

            List<CassandraIngredient> actual = tacoLoaderImpl.loadIngredients(ingredientIds);

            assertThat(actual)
                .isNotNull()
                .isSameAs(ingredients);
        });
    }
}