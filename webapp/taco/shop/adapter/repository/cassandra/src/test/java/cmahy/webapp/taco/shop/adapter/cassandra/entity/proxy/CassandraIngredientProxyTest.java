package cmahy.webapp.taco.shop.adapter.cassandra.entity.proxy;

import cmahy.common.helper.Generator;
import cmahy.webapp.taco.shop.adapter.cassandra.entity.domain.CassandraIngredient;
import cmahy.webapp.taco.shop.kernel.domain.IngredientType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CassandraIngredientProxyTest {

    @Mock
    private CassandraIngredient ingredient;

    @Test
    void unwrap() {
        assertDoesNotThrow(() -> {
            assertThat(new CassandraIngredientProxy(ingredient).unwrap()).isSameAs(ingredient);
        });
    }

    @Test
    void setters() {
        assertDoesNotThrow(() -> {

            CassandraIngredientProxy actual = new CassandraIngredientProxy(ingredient);

            String name = Generator.generateAString();
            IngredientType type = Generator.randomEnum(IngredientType.class);

            actual.setName(name);
            actual.setType(type);

            verify(ingredient).setName(name);
            verify(ingredient).setType(type);

            verify(ingredient, never()).setId(any(UUID.class));

            verifyNoMoreInteractions(ingredient);
        });
    }

    @Test
    void getters() {
        assertDoesNotThrow(() -> {

            CassandraIngredientProxy actual = new CassandraIngredientProxy(ingredient);

            actual.getId();
            actual.getName();
            actual.getType();

            verify(ingredient).getId();
            verify(ingredient).getName();
            verify(ingredient).getType();

            verifyNoMoreInteractions(ingredient);
        });
    }
}