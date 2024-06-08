package cmahy.webapp.resource.impl.adapter.api.taco.shop;

import cmahy.common.helper.Generator;
import cmahy.webapp.resource.api.taco.shop.vo.id.IngredientApiId;
import cmahy.webapp.resource.api.taco.shop.vo.input.IngredientCreateApiVo;
import cmahy.webapp.resource.api.taco.shop.vo.input.IngredientUpdateApiVo;
import cmahy.webapp.resource.api.taco.shop.vo.output.IngredientOutputApiVo;
import cmahy.webapp.resource.api.taco.shop.vo.output.IngredientPageOutputApiVo;
import cmahy.webapp.resource.impl.adapter.api.taco.shop.mapper.input.*;
import cmahy.webapp.resource.impl.adapter.api.taco.shop.mapper.output.IngredientOutputAppMapper;
import cmahy.webapp.resource.impl.adapter.api.taco.shop.mapper.output.IngredientPageOutputAppMapper;
import cmahy.webapp.resource.impl.adapter.config.properties.PaginationProperties;
import cmahy.webapp.resource.impl.application.taco.shop.command.*;
import cmahy.webapp.resource.impl.application.taco.shop.query.GetAllIngredientPagedQuery;
import cmahy.webapp.resource.impl.application.taco.shop.vo.input.IngredientCreateInputAppVo;
import cmahy.webapp.resource.impl.application.taco.shop.vo.input.IngredientUpdateInputAppVo;
import cmahy.webapp.resource.impl.application.taco.shop.vo.output.IngredientOutputAppVo;
import cmahy.webapp.resource.impl.application.taco.shop.vo.output.IngredientPageOutputAppVo;
import cmahy.webapp.resource.impl.application.vo.input.PageableInputAppVo;
import cmahy.webapp.resource.impl.domain.taco.id.IngredientId;
import cmahy.webapp.resource.impl.exception.NullException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientApiImplTest {

    @Mock
    private PaginationProperties paginationProperties;

    @Mock
    private GetAllIngredientPagedQuery getAllIngredientPagedQuery;

    @Mock
    private IngredientPageOutputAppMapper ingredientPageOutputAppMapper;

    @Mock
    private IngredientApiIdMapper ingredientApiIdMapper;

    @Mock
    private IngredientCreateInputApiMapper ingredientCreateInputApiMapper;

    @Mock
    private IngredientUpdateInputApiMapper ingredientUpdateInputApiMapper;

    @Mock
    private CreateIngredientCommand createIngredientCommand;

    @Mock
    private PartialUpdatingAnIngredientCommand partialUpdatingAnIngredientCommand;

    @Mock
    private DeleteAnIngredientCommand deleteAnIngredientCommand;

    @Mock
    private IngredientOutputAppMapper ingredientOutputAppMapper;

    @InjectMocks
    private IngredientApiImpl ingredientApiImpl;

    @Test
    void getAll() {
        assertDoesNotThrow(() -> {
            Integer pageNumber = Generator.randomIntEqualOrAboveZero();
            Integer pageSize = Generator.randomIntEqualOrAboveZero();

            IngredientPageOutputAppVo ingredientPageOutputAppVo = mock(IngredientPageOutputAppVo.class);
            IngredientPageOutputApiVo ingredientPageOutputApiVo = mock(IngredientPageOutputApiVo.class);

            when(paginationProperties.pageSize()).thenReturn(Generator.randomIntEqualOrAboveZero());

            ArgumentCaptor<PageableInputAppVo> captor = ArgumentCaptor.forClass(PageableInputAppVo.class);

            when(getAllIngredientPagedQuery.execute(captor.capture())).thenReturn(ingredientPageOutputAppVo);
            when(ingredientPageOutputAppMapper.map(ingredientPageOutputAppVo)).thenReturn(ingredientPageOutputApiVo);

            IngredientPageOutputApiVo actual = ingredientApiImpl.getAll(pageNumber, pageSize);

            assertThat(actual)
                .isNotNull()
                .isEqualTo(ingredientPageOutputApiVo);

            assertThat(captor.getAllValues()).hasSize(1);
            assertThat(captor.getValue().pageNumber()).isEqualTo(pageNumber);
            assertThat(captor.getValue().pageSize()).isEqualTo(pageSize);
        });
    }

    @Test
    void create() {
        assertDoesNotThrow(() -> {
            IngredientCreateApiVo createVo = mock(IngredientCreateApiVo.class);
            IngredientCreateInputAppVo createAppVo = mock(IngredientCreateInputAppVo.class);
            IngredientOutputAppVo outputAppVo = mock(IngredientOutputAppVo.class);
            IngredientOutputApiVo outputApiVo = mock(IngredientOutputApiVo.class);

            when(ingredientCreateInputApiMapper.map(createVo)).thenReturn(createAppVo);
            when(createIngredientCommand.execute(createAppVo)).thenReturn(outputAppVo);
            when(ingredientOutputAppMapper.map(outputAppVo)).thenReturn(outputApiVo);

            IngredientOutputApiVo actual = ingredientApiImpl.create(createVo);

            assertThat(actual)
                .isNotNull()
                .isEqualTo(outputApiVo);
        });
    }

    @Test
    void update() {
        assertDoesNotThrow(() -> {
            IngredientUpdateApiVo updateVo = mock(IngredientUpdateApiVo.class);
            IngredientApiId apiId = mock(IngredientApiId.class);
            IngredientUpdateInputAppVo updateAppVo = mock(IngredientUpdateInputAppVo.class);
            IngredientId id = mock(IngredientId.class);
            IngredientOutputAppVo outputAppVo = mock(IngredientOutputAppVo.class);
            IngredientOutputApiVo outputApiVo = mock(IngredientOutputApiVo.class);

            when(ingredientApiIdMapper.map(apiId)).thenReturn(id);
            when(ingredientUpdateInputApiMapper.map(updateVo)).thenReturn(updateAppVo);
            when(partialUpdatingAnIngredientCommand.execute(id, updateAppVo)).thenReturn(outputAppVo);
            when(ingredientOutputAppMapper.map(outputAppVo)).thenReturn(outputApiVo);

            IngredientOutputApiVo actual = ingredientApiImpl.update(apiId, updateVo);

            assertThat(actual)
                .isNotNull()
                .isEqualTo(outputApiVo);
        });
    }

    @Test
    void delete() {
        assertDoesNotThrow(() -> {
            IngredientApiId apiId = mock(IngredientApiId.class);
            IngredientId id = mock(IngredientId.class);

            when(ingredientApiIdMapper.map(apiId)).thenReturn(id);

            ingredientApiImpl.delete(apiId);

            verify(deleteAnIngredientCommand).execute(id);
        });
    }

    @Test
    void delete_whenGivenIdIsNull_thenThrowNullException() {
        assertThrows(NullException.class, () -> {
            when(ingredientApiIdMapper.map(null)).thenThrow(NullException.class);

            ingredientApiImpl.delete(null);
        });

        verifyNoInteractions(deleteAnIngredientCommand);
    }
}