package cmahy.webapp.kernel.taco.shop.impl.application.service;

import cmahy.common.helper.Generator;
import cmahy.webapp.taco.shop.kernel.application.mapper.output.IngredientOutputMapper;
import cmahy.webapp.taco.shop.kernel.application.repository.IngredientRepository;
import cmahy.webapp.taco.shop.kernel.application.service.CreateAnIngredient;
import cmahy.webapp.taco.shop.kernel.application.service.IngredientFactory;
import cmahy.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.webapp.taco.shop.kernel.domain.IngredientType;
import cmahy.webapp.taco.shop.kernel.exception.ingredient.IngredientDuplicateException;
import cmahy.webapp.taco.shop.kernel.exception.ingredient.IngredientValidationException;
import cmahy.webapp.taco.shop.kernel.vo.input.IngredientCreateInputVo;
import cmahy.webapp.taco.shop.kernel.vo.output.IngredientOutputVo;
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
            IngredientType type = Generator.randomEnum(IngredientType.class);

            IngredientCreateInputVo IngredientCreateInputVo = mock(IngredientCreateInputVo.class);
            Ingredient ingredient = new Ingredient(
                id,
                name,
                type
            );
            IngredientOutputVo IngredientOutputVo = mock(IngredientOutputVo.class);

            when(ingredientFactory.create(IngredientCreateInputVo)).thenReturn(ingredient);
            when(ingredientRepository.findByNameAndType(name, type)).thenReturn(Optional.empty());
            when(ingredientRepository.save(ingredient)).thenReturn(ingredient);
            when(ingredientOutputMapper.map(ingredient)).thenReturn(IngredientOutputVo);

            IngredientOutputVo actual = createAnIngredient.execute(IngredientCreateInputVo);

            assertThat(actual)
                .isNotNull()
                .isEqualTo(IngredientOutputVo);
        });
    }

    @Test
    void execute_onIngredientExistsWithSameNameAndType_thenThrowDuplicateException() {
        assertThrows(IngredientDuplicateException.class, () -> {
            String id = UUID.randomUUID().toString();
            String name = Generator.generateAString();
            IngredientType type = Generator.randomEnum(IngredientType.class);

            IngredientCreateInputVo IngredientCreateInputVo = mock(IngredientCreateInputVo.class);
            Ingredient ingredient = new Ingredient(
                id,
                name,
                type
            );

            when(ingredientFactory.create(IngredientCreateInputVo)).thenReturn(ingredient);
            when(ingredientRepository.findByNameAndType(name, type)).thenReturn(Optional.of(ingredient));

            createAnIngredient.execute(IngredientCreateInputVo);
        });

        verify(ingredientRepository, never()).save(any(Ingredient.class));
    }

    @Test
    void execute_onValidationFailed_thenThrowValidationException() {
        assertThrows(IngredientValidationException.class, () -> {
            IngredientCreateInputVo IngredientCreateInputVo = mock(IngredientCreateInputVo.class);

            when(ingredientFactory.create(IngredientCreateInputVo)).thenAnswer(_ -> {
                 throw new IngredientValidationException(Generator.generateAString());
            });

            createAnIngredient.execute(IngredientCreateInputVo);
        });

        verifyNoInteractions(ingredientRepository);
    }
}