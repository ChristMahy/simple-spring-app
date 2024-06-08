package cmahy.webapp.resource.impl.application.taco.shop.service;

import cmahy.common.helper.Generator;
import cmahy.webapp.resource.impl.application.taco.shop.mapper.output.IngredientOutputMapper;
import cmahy.webapp.resource.impl.application.taco.shop.repository.IngredientRepository;
import cmahy.webapp.resource.impl.application.taco.shop.vo.input.IngredientUpdateInputAppVo;
import cmahy.webapp.resource.impl.application.taco.shop.vo.output.IngredientOutputAppVo;
import cmahy.webapp.resource.impl.domain.taco.Ingredient;
import cmahy.webapp.resource.impl.domain.taco.id.IngredientId;
import cmahy.webapp.resource.impl.exception.taco.IngredientNotFoundException;
import cmahy.webapp.resource.impl.helper.ValidatorHelperExtension;
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

@ExtendWith({ValidatorHelperExtension.class, MockitoExtension.class})
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
            Ingredient.Type previousType = Generator.randomEnum(Ingredient.Type.class);

            Ingredient.Type type = Generator.randomEnum(Ingredient.Type.class);
            String name = Generator.generateAStringWithoutSpecialChars();

            IngredientId ingredientId = new IngredientId(Generator.generateAString());
            IngredientUpdateInputAppVo ingredientUpdateInputAppVo = new IngredientUpdateInputAppVo(
                Optional.of(name),
                Optional.of(type.name())
            );
            Ingredient ingredient = new Ingredient()
                .setId(previousId)
                .setName(previousName)
                .setType(previousType);
            IngredientOutputAppVo ingredientOutputAppVo = mock(IngredientOutputAppVo.class);

            when(ingredientRepository.findById(ingredientId.value())).thenReturn(Optional.of(ingredient));
            when(ingredientRepository.save(ingredient)).thenReturn(ingredient);
            when(ingredientOutputMapper.map(ingredient)).thenReturn(ingredientOutputAppVo);

            IngredientOutputAppVo actual = partialUpdatingAnIngredient.execute(ingredientId, ingredientUpdateInputAppVo);

            assertThat(actual).isEqualTo(ingredientOutputAppVo);

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
            Ingredient.Type previousType = Generator.randomEnum(Ingredient.Type.class);

            IngredientId ingredientId = new IngredientId(Generator.generateAString());
            IngredientUpdateInputAppVo ingredientUpdateInputAppVo = new IngredientUpdateInputAppVo(
                Optional.empty(),
                Optional.empty()
            );
            Ingredient ingredient = new Ingredient()
                .setId(previousId)
                .setName(previousName)
                .setType(previousType);
            IngredientOutputAppVo ingredientOutputAppVo = mock(IngredientOutputAppVo.class);

            when(ingredientRepository.findById(ingredientId.value())).thenReturn(Optional.of(ingredient));
            when(ingredientRepository.save(ingredient)).thenReturn(ingredient);
            when(ingredientOutputMapper.map(ingredient)).thenReturn(ingredientOutputAppVo);

            IngredientOutputAppVo actual = partialUpdatingAnIngredient.execute(ingredientId, ingredientUpdateInputAppVo);

            assertThat(actual).isEqualTo(ingredientOutputAppVo);

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
            Ingredient.Type previousType = Generator.randomEnum(Ingredient.Type.class);

            IngredientId ingredientId = new IngredientId(Generator.generateAString());
            IngredientUpdateInputAppVo ingredientUpdateInputAppVo = new IngredientUpdateInputAppVo(
                Optional.empty(),
                Optional.of(Generator.generateAString(500))
            );
            Ingredient ingredient = new Ingredient()
                .setId(previousId)
                .setName(previousName)
                .setType(previousType);
            IngredientOutputAppVo ingredientOutputAppVo = mock(IngredientOutputAppVo.class);

            when(ingredientRepository.findById(ingredientId.value())).thenReturn(Optional.of(ingredient));
            when(ingredientRepository.save(ingredient)).thenReturn(ingredient);
            when(ingredientOutputMapper.map(ingredient)).thenReturn(ingredientOutputAppVo);

            partialUpdatingAnIngredient.execute(ingredientId, ingredientUpdateInputAppVo);

            assertThat(ingredient.getName()).isEqualTo(previousName);
            assertThat(ingredient.getType()).isEqualTo(previousType);
        });
    }

    @Test
    void execute_onAllBlankFields_thenDoNotUpdateOriginal() {
        assertDoesNotThrow(() -> {
            String previousId = Generator.generateAStringWithoutSpecialChars();
            String previousName = Generator.generateAStringWithoutSpecialChars();
            Ingredient.Type previousType = Generator.randomEnum(Ingredient.Type.class);

            IngredientId ingredientId = new IngredientId(Generator.generateAString());
            IngredientUpdateInputAppVo ingredientUpdateInputAppVo = new IngredientUpdateInputAppVo(
                Optional.of("      \t      "),
                Optional.of("       \t     ")
            );
            Ingredient ingredient = new Ingredient()
                .setId(previousId)
                .setName(previousName)
                .setType(previousType);
            IngredientOutputAppVo ingredientOutputAppVo = mock(IngredientOutputAppVo.class);

            when(ingredientRepository.findById(ingredientId.value())).thenReturn(Optional.of(ingredient));
            when(ingredientRepository.save(ingredient)).thenReturn(ingredient);
            when(ingredientOutputMapper.map(ingredient)).thenReturn(ingredientOutputAppVo);

            partialUpdatingAnIngredient.execute(ingredientId, ingredientUpdateInputAppVo);

            assertThat(ingredient.getName()).isEqualTo(previousName);
            assertThat(ingredient.getType()).isEqualTo(previousType);
        });
    }

    @Test
    void execute_whenValuesHaveSpaceBeforeAndAfter_thenTrimSpaceAndSaveIngredient() {
        assertDoesNotThrow(() -> {
            String previousId = Generator.generateAStringWithoutSpecialChars();
            String previousName = Generator.generateAStringWithoutSpecialChars();
            Ingredient.Type previousType = Generator.randomEnum(Ingredient.Type.class);

            Ingredient.Type type = Generator.randomEnum(Ingredient.Type.class);
            String name = Generator.generateAStringWithoutSpecialChars();

            IngredientId ingredientId = new IngredientId(Generator.generateAString());
            IngredientUpdateInputAppVo ingredientUpdateInputAppVo = new IngredientUpdateInputAppVo(
                Optional.of("       \t  " + name + "     \t     "),
                Optional.of("       \t  " + type.name() + "     \t     ")
            );
            Ingredient ingredient = new Ingredient()
                .setId(previousId)
                .setName(previousName)
                .setType(previousType);
            IngredientOutputAppVo ingredientOutputAppVo = mock(IngredientOutputAppVo.class);

            when(ingredientRepository.findById(ingredientId.value())).thenReturn(Optional.of(ingredient));
            when(ingredientRepository.save(ingredient)).thenReturn(ingredient);
            when(ingredientOutputMapper.map(ingredient)).thenReturn(ingredientOutputAppVo);

            IngredientOutputAppVo actual = partialUpdatingAnIngredient.execute(ingredientId, ingredientUpdateInputAppVo);

            assertThat(actual).isEqualTo(ingredientOutputAppVo);

            assertThat(ingredient.getId()).isEqualTo(previousId);
            assertThat(ingredient.getName()).isEqualTo(name);
            assertThat(ingredient.getType()).isEqualTo(type);
        });
    }

    @Test
    void execute_whenIngredientNotFound_thenThrowNotFoundException() {
        assertThrows(IngredientNotFoundException.class, () -> {
            IngredientId ingredientId = new IngredientId(Generator.generateAString());
            IngredientUpdateInputAppVo ingredientUpdateInputAppVo = mock(IngredientUpdateInputAppVo.class);

            when(ingredientRepository.findById(ingredientId.value())).thenReturn(Optional.empty());

            partialUpdatingAnIngredient.execute(ingredientId, ingredientUpdateInputAppVo);
        });

        verify(ingredientRepository, never()).save(any(Ingredient.class));
    }
}