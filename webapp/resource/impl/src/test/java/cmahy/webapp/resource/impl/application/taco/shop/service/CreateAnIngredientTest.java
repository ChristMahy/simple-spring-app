package cmahy.webapp.resource.impl.application.taco.shop.service;

import cmahy.common.helper.Generator;
import cmahy.webapp.resource.impl.application.taco.shop.mapper.output.IngredientOutputMapper;
import cmahy.webapp.resource.impl.application.taco.shop.repository.IngredientRepository;
import cmahy.webapp.resource.impl.application.taco.shop.vo.input.IngredientCreateInputAppVo;
import cmahy.webapp.resource.impl.application.taco.shop.vo.output.IngredientOutputAppVo;
import cmahy.webapp.resource.impl.domain.taco.Ingredient;
import cmahy.webapp.resource.impl.exception.taco.IngredientDuplicateException;
import cmahy.webapp.resource.impl.exception.taco.IngredientValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class CreateAnIngredientTest {

    @Mock
    private IngredientFactory ingredientFactory;

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private IngredientOutputMapper ingredientOutputMapper;

    @InjectMocks
    private CreateAnIngredient createAnIngredient;

    @Test
    void execute() {
        assertDoesNotThrow(() -> {
            String id = UUID.randomUUID().toString();
            String name = Generator.generateAString();
            Ingredient.Type type = Generator.randomEnum(Ingredient.Type.class);

            IngredientCreateInputAppVo ingredientCreateInputAppVo = mock(IngredientCreateInputAppVo.class);
            Ingredient ingredient = new Ingredient(
                id,
                name,
                type
            );
            IngredientOutputAppVo ingredientOutputAppVo = mock(IngredientOutputAppVo.class);

            when(ingredientFactory.create(ingredientCreateInputAppVo)).thenReturn(ingredient);
            when(ingredientRepository.findByNameAndType(name, type)).thenReturn(Optional.empty());
            when(ingredientRepository.save(ingredient)).thenReturn(ingredient);
            when(ingredientOutputMapper.map(ingredient)).thenReturn(ingredientOutputAppVo);

            IngredientOutputAppVo actual = createAnIngredient.execute(ingredientCreateInputAppVo);

            assertThat(actual)
                .isNotNull()
                .isEqualTo(ingredientOutputAppVo);
        });
    }

    @Test
    void execute_onIngredientExistsWithSameNameAndType_thenThrowDuplicateException() {
        assertThrows(IngredientDuplicateException.class, () -> {
            String id = UUID.randomUUID().toString();
            String name = Generator.generateAString();
            Ingredient.Type type = Generator.randomEnum(Ingredient.Type.class);

            IngredientCreateInputAppVo ingredientCreateInputAppVo = mock(IngredientCreateInputAppVo.class);
            Ingredient ingredient = new Ingredient(
                id,
                name,
                type
            );

            when(ingredientFactory.create(ingredientCreateInputAppVo)).thenReturn(ingredient);
            when(ingredientRepository.findByNameAndType(name, type)).thenReturn(Optional.of(ingredient));

            createAnIngredient.execute(ingredientCreateInputAppVo);
        });

        verify(ingredientRepository, never()).save(any(Ingredient.class));
    }

    @Test
    void execute_onValidationFailed_thenThrowValidationException() {
        assertThrows(IngredientValidationException.class, () -> {
            IngredientCreateInputAppVo ingredientCreateInputAppVo = mock(IngredientCreateInputAppVo.class);

            when(ingredientFactory.create(ingredientCreateInputAppVo)).thenThrow(IngredientValidationException.class);

            createAnIngredient.execute(ingredientCreateInputAppVo);
        });

        verifyNoInteractions(ingredientRepository);
    }
}