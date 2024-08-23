package cmahy.webapp.kernel.taco.shop.impl.application.service;

import cmahy.common.helper.Generator;
import cmahy.webapp.taco.shop.kernel.application.mapper.output.IngredientOutputMapper;
import cmahy.webapp.taco.shop.kernel.application.repository.IngredientRepository;
import cmahy.webapp.taco.shop.kernel.application.service.PartialUpdatingAnIngredient;
import cmahy.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.webapp.taco.shop.kernel.domain.IngredientType;
import cmahy.webapp.taco.shop.kernel.domain.id.IngredientId;
import cmahy.webapp.taco.shop.kernel.exception.ingredient.IngredientNotFoundException;
import cmahy.webapp.taco.shop.kernel.vo.input.IngredientUpdateInputVo;
import cmahy.webapp.taco.shop.kernel.vo.output.IngredientOutputVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class PartialUpdatingAnIngredientTest {

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private IngredientOutputMapper ingredientOutputMapper;

    @InjectMocks
    private PartialUpdatingAnIngredient partialUpdatingAnIngredient;

    @Test
    void execute_onFullUpdate_thenUpdateAllFields() {
        assertDoesNotThrow(() -> {
            String previousId = Generator.generateAStringWithoutSpecialChars();
            String previousName = Generator.generateAStringWithoutSpecialChars();
            IngredientType previousType = Generator.randomEnum(IngredientType.class);

            IngredientType type = Generator.randomEnum(IngredientType.class);
            String name = Generator.generateAStringWithoutSpecialChars();

            IngredientId ingredientId = new IngredientId(Generator.generateAString());
            IngredientUpdateInputVo ingredientUpdateInputVo = new IngredientUpdateInputVo(
                Optional.of(name),
                Optional.of(type.name())
            );
            Ingredient ingredient = new Ingredient()
                .setId(previousId)
                .setName(previousName)
                .setType(previousType);
            IngredientOutputVo ingredientOutputVo = mock(IngredientOutputVo.class);

            when(ingredientRepository.findById(ingredientId)).thenReturn(Optional.of(ingredient));
            when(ingredientRepository.save(ingredient)).thenReturn(ingredient);
            when(ingredientOutputMapper.map(ingredient)).thenReturn(ingredientOutputVo);

            IngredientOutputVo actual = partialUpdatingAnIngredient.execute(ingredientId, ingredientUpdateInputVo);

            assertThat(actual).isEqualTo(ingredientOutputVo);

            assertThat(ingredient.getId()).isEqualTo(previousId);
            assertThat(ingredient.getName()).isEqualTo(name);
            assertThat(ingredient.getType()).isEqualTo(type);
        });
    }

    @Test
    void execute_onAllEmptyFields_thenDoNotUpdateOriginal() {
        assertDoesNotThrow(() -> {

            String previousId = Generator.generateAStringWithoutSpecialChars();
            String previousName = Generator.generateAStringWithoutSpecialChars();
            IngredientType previousType = Generator.randomEnum(IngredientType.class);

            IngredientId ingredientId = new IngredientId(Generator.generateAString());
            IngredientUpdateInputVo ingredientUpdateInputVo = new IngredientUpdateInputVo(
                Optional.empty(),
                Optional.empty()
            );
            Ingredient ingredient = new Ingredient()
                .setId(previousId)
                .setName(previousName)
                .setType(previousType);
            IngredientOutputVo ingredientOutputVo = mock(IngredientOutputVo.class);

            when(ingredientRepository.findById(ingredientId)).thenReturn(Optional.of(ingredient));
            when(ingredientRepository.save(ingredient)).thenReturn(ingredient);
            when(ingredientOutputMapper.map(ingredient)).thenReturn(ingredientOutputVo);

            IngredientOutputVo actual = partialUpdatingAnIngredient.execute(ingredientId, ingredientUpdateInputVo);

            assertThat(actual).isEqualTo(ingredientOutputVo);

            assertThat(ingredient.getId()).isEqualTo(previousId);
            assertThat(ingredient.getName()).isEqualTo(previousName);
            assertThat(ingredient.getType()).isEqualTo(previousType);
        });
    }

    @Test
    void execute_onEnumValueDoesNotExist_thenDoNotUpdateOriginal() {
        assertDoesNotThrow(() -> {
            String previousId = Generator.generateAStringWithoutSpecialChars();
            String previousName = Generator.generateAStringWithoutSpecialChars();
            IngredientType previousType = Generator.randomEnum(IngredientType.class);

            IngredientId ingredientId = new IngredientId(Generator.generateAString());
            IngredientUpdateInputVo ingredientUpdateInputVo = new IngredientUpdateInputVo(
                Optional.empty(),
                Optional.of(Generator.generateAString(500))
            );
            Ingredient ingredient = new Ingredient()
                .setId(previousId)
                .setName(previousName)
                .setType(previousType);
            IngredientOutputVo ingredientOutputVo = mock(IngredientOutputVo.class);

            when(ingredientRepository.findById(ingredientId)).thenReturn(Optional.of(ingredient));
            when(ingredientRepository.save(ingredient)).thenReturn(ingredient);
            when(ingredientOutputMapper.map(ingredient)).thenReturn(ingredientOutputVo);

            partialUpdatingAnIngredient.execute(ingredientId, ingredientUpdateInputVo);

            assertThat(ingredient.getName()).isEqualTo(previousName);
            assertThat(ingredient.getType()).isEqualTo(previousType);
        });
    }

    @Test
    void execute_onAllBlankFields_thenDoNotUpdateOriginal() {
        assertDoesNotThrow(() -> {
            String previousId = Generator.generateAStringWithoutSpecialChars();
            String previousName = Generator.generateAStringWithoutSpecialChars();
            IngredientType previousType = Generator.randomEnum(IngredientType.class);

            IngredientId ingredientId = new IngredientId(Generator.generateAString());
            IngredientUpdateInputVo ingredientUpdateInputVo = new IngredientUpdateInputVo(
                Optional.of("      \t      "),
                Optional.of("       \t     ")
            );
            Ingredient ingredient = new Ingredient()
                .setId(previousId)
                .setName(previousName)
                .setType(previousType);
            IngredientOutputVo ingredientOutputVo = mock(IngredientOutputVo.class);

            when(ingredientRepository.findById(ingredientId)).thenReturn(Optional.of(ingredient));
            when(ingredientRepository.save(ingredient)).thenReturn(ingredient);
            when(ingredientOutputMapper.map(ingredient)).thenReturn(ingredientOutputVo);

            partialUpdatingAnIngredient.execute(ingredientId, ingredientUpdateInputVo);

            assertThat(ingredient.getName()).isEqualTo(previousName);
            assertThat(ingredient.getType()).isEqualTo(previousType);
        });
    }

    @Test
    void execute_whenValuesHaveSpaceBeforeAndAfter_thenTrimSpaceAndSaveIngredient() {
        assertDoesNotThrow(() -> {
            String previousId = Generator.generateAStringWithoutSpecialChars();
            String previousName = Generator.generateAStringWithoutSpecialChars();
            IngredientType previousType = Generator.randomEnum(IngredientType.class);

            IngredientType type = Generator.randomEnum(IngredientType.class);
            String name = Generator.generateAStringWithoutSpecialChars();

            IngredientId ingredientId = new IngredientId(Generator.generateAString());
            IngredientUpdateInputVo ingredientUpdateInputVo = new IngredientUpdateInputVo(
                Optional.of("       \t  " + name + "     \t     "),
                Optional.of("       \t  " + type.name() + "     \t     ")
            );
            Ingredient ingredient = new Ingredient()
                .setId(previousId)
                .setName(previousName)
                .setType(previousType);
            IngredientOutputVo ingredientOutputVo = mock(IngredientOutputVo.class);

            when(ingredientRepository.findById(ingredientId)).thenReturn(Optional.of(ingredient));
            when(ingredientRepository.save(ingredient)).thenReturn(ingredient);
            when(ingredientOutputMapper.map(ingredient)).thenReturn(ingredientOutputVo);

            IngredientOutputVo actual = partialUpdatingAnIngredient.execute(ingredientId, ingredientUpdateInputVo);

            assertThat(actual).isEqualTo(ingredientOutputVo);

            assertThat(ingredient.getId()).isEqualTo(previousId);
            assertThat(ingredient.getName()).isEqualTo(name);
            assertThat(ingredient.getType()).isEqualTo(type);
        });
    }

    @Test
    void execute_whenIngredientNotFound_thenThrowNotFoundException() {
        assertThrows(IngredientNotFoundException.class, () -> {
            IngredientId ingredientId = new IngredientId(Generator.generateAString());
            IngredientUpdateInputVo ingredientUpdateInputVo = mock(IngredientUpdateInputVo.class);

            when(ingredientRepository.findById(ingredientId)).thenReturn(Optional.empty());

            partialUpdatingAnIngredient.execute(ingredientId, ingredientUpdateInputVo);
        });

        verify(ingredientRepository, never()).save(any(Ingredient.class));
    }
}