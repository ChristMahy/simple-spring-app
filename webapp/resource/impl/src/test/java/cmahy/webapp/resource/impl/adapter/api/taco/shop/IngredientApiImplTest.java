package cmahy.webapp.resource.impl.adapter.api.taco.shop;

import cmahy.common.entity.page.EntityPageable;
import cmahy.common.helper.Generator;
import cmahy.webapp.resource.impl.adapter.config.properties.PaginationProperties;
import cmahy.webapp.taco.shop.kernel.application.command.*;
import cmahy.webapp.taco.shop.kernel.application.query.GetAllIngredientPagedQuery;
import cmahy.webapp.taco.shop.kernel.domain.id.IngredientId;
import cmahy.webapp.taco.shop.kernel.vo.input.IngredientCreateInputVo;
import cmahy.webapp.taco.shop.kernel.vo.input.IngredientUpdateInputVo;
import cmahy.webapp.taco.shop.kernel.vo.output.IngredientOutputVo;
import cmahy.webapp.taco.shop.kernel.vo.output.IngredientPageOutputVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientApiImplTest {

    @Mock
    private PaginationProperties paginationProperties;

    @Mock
    private GetAllIngredientPagedQuery getAllIngredientPagedQuery;

    @Mock
    private CreateIngredientCommand createIngredientCommand;

    @Mock
    private PartialUpdatingAnIngredientCommand partialUpdatingAnIngredientCommand;

    @Mock
    private DeleteAnIngredientCommand deleteAnIngredientCommand;

    @InjectMocks
    private IngredientApiImpl ingredientApiImpl;

    @Test
    void getAll() {
        assertDoesNotThrow(() -> {
            Integer pageNumber = Generator.randomIntEqualOrAboveZero();
            Integer pageSize = Generator.randomIntEqualOrAboveZero();

            IngredientPageOutputVo ingredientPageOutputVo = mock(IngredientPageOutputVo.class);

            when(paginationProperties.pageSize()).thenReturn(Generator.randomIntEqualOrAboveZero());

            ArgumentCaptor<EntityPageable> captor = ArgumentCaptor.forClass(EntityPageable.class);

            when(getAllIngredientPagedQuery.execute(captor.capture())).thenReturn(ingredientPageOutputVo);

            IngredientPageOutputVo actual = ingredientApiImpl.getAll(pageNumber, pageSize);

            assertThat(actual)
                .isNotNull()
                .isEqualTo(ingredientPageOutputVo);

            assertThat(captor.getAllValues()).hasSize(1);
            assertThat(captor.getValue().pageNumber()).isEqualTo(pageNumber);
            assertThat(captor.getValue().pageSize()).isEqualTo(pageSize);
        });
    }

    @Test
    void create() {
        assertDoesNotThrow(() -> {
            IngredientCreateInputVo createVo = mock(IngredientCreateInputVo.class);
            IngredientOutputVo outputVo = mock(IngredientOutputVo.class);

            when(createIngredientCommand.execute(createVo)).thenReturn(outputVo);

            IngredientOutputVo actual = ingredientApiImpl.create(createVo);

            assertThat(actual)
                .isNotNull()
                .isEqualTo(outputVo);
        });
    }

    @Test
    void update() {
        assertDoesNotThrow(() -> {
            IngredientUpdateInputVo updateVo = mock(IngredientUpdateInputVo.class);
            IngredientId id = mock(IngredientId.class);
            IngredientOutputVo outputVo = mock(IngredientOutputVo.class);

            when(partialUpdatingAnIngredientCommand.execute(id, updateVo)).thenReturn(outputVo);

            IngredientOutputVo actual = ingredientApiImpl.update(id, updateVo);

            assertThat(actual)
                .isNotNull()
                .isEqualTo(outputVo);
        });
    }

    @Test
    void delete() {
        assertDoesNotThrow(() -> {
            IngredientId id = mock(IngredientId.class);

            ingredientApiImpl.delete(id);

            verify(deleteAnIngredientCommand).execute(id);
        });
    }
}