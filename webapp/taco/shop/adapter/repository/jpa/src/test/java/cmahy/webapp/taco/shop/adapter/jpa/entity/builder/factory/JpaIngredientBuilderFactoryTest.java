package cmahy.webapp.taco.shop.adapter.jpa.entity.builder.factory;

import cmahy.webapp.taco.shop.adapter.jpa.entity.JpaIngredient;
import cmahy.webapp.taco.shop.adapter.jpa.entity.builder.JpaIngredientBuilder;
import cmahy.webapp.taco.shop.kernel.domain.Ingredient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.RETURNS_SELF;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class JpaIngredientBuilderFactoryTest {

    @InjectMocks
    private JpaIngredientBuilderFactory jpaIngredientBuilderFactory;

    @Test
    void create() {
        assertDoesNotThrow(() -> {
            JpaIngredientBuilder actual = jpaIngredientBuilderFactory.create();

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(JpaIngredientBuilder.class);
        });
    }

    @Test
    void createWithOriginal() {
        assertDoesNotThrow(() -> {
            JpaIngredient original = mock(JpaIngredient.class, RETURNS_SELF);

            JpaIngredientBuilder actual = jpaIngredientBuilderFactory.create(original);

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(JpaIngredientBuilder.class);

            Ingredient actualBuilderResult = actual.build();

            assertThat(actualBuilderResult).isSameAs(original);
        });
    }
}