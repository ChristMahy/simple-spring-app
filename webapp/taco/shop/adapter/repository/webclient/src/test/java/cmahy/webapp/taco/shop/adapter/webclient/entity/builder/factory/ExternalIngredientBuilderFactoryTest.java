package cmahy.webapp.taco.shop.adapter.webclient.entity.builder.factory;

import cmahy.webapp.taco.shop.adapter.webclient.entity.domain.ExternalIngredient;
import cmahy.webapp.taco.shop.adapter.webclient.entity.builder.ExternalIngredientBuilder;
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
class ExternalIngredientBuilderFactoryTest {

    @InjectMocks
    private ExternalIngredientBuilderFactory externalIngredientBuilderFactory;

    @Test
    void create() {
        assertDoesNotThrow(() -> {
            ExternalIngredientBuilder actual = externalIngredientBuilderFactory.create();

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(ExternalIngredientBuilder.class);
        });
    }

    @Test
    void createWithOriginal() {
        assertDoesNotThrow(() -> {
            ExternalIngredient original = mock(ExternalIngredient.class, RETURNS_SELF);

            ExternalIngredientBuilder actual = externalIngredientBuilderFactory.create(original);

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(ExternalIngredientBuilder.class);

            Ingredient actualBuilderResult = actual.build();

            assertThat(actualBuilderResult).isSameAs(original);
        });
    }
}