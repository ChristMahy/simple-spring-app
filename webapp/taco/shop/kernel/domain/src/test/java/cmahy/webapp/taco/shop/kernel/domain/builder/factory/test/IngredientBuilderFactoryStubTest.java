package cmahy.webapp.taco.shop.kernel.domain.builder.factory.test;

import cmahy.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.webapp.taco.shop.kernel.domain.IngredientStub;
import cmahy.webapp.taco.shop.kernel.domain.builder.IngredientBuilderStub;
import cmahy.webapp.taco.shop.kernel.domain.builder.factory.IngredientBuilderFactoryStub;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.RETURNS_SELF;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class IngredientBuilderFactoryStubTest {

    @InjectMocks
    private IngredientBuilderFactoryStub ingredientBuilderFactoryStub;

    @Test
    void create() {
        assertDoesNotThrow(() -> {
            IngredientBuilderStub actual = ingredientBuilderFactoryStub.create();

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(IngredientBuilderStub.class);
        });
    }

    @Test
    void createWithOriginal() {
        assertDoesNotThrow(() -> {
            IngredientStub original = mock(IngredientStub.class, RETURNS_SELF);

            IngredientBuilderStub actual = ingredientBuilderFactoryStub.create(original);

            assertThat(actual)
                .isNotNull()
                .isInstanceOf(IngredientBuilderStub.class);

            Ingredient actualBuilderResult = actual.build();

            assertThat(actualBuilderResult).isSameAs(original);
        });
    }
}