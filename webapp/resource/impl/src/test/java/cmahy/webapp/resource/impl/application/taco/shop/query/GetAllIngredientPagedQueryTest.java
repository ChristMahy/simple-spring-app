package cmahy.webapp.resource.impl.application.taco.shop.query;

import cmahy.webapp.resource.impl.application.taco.shop.mapper.output.IngredientPageOutputMapper;
import cmahy.webapp.resource.impl.application.taco.shop.repository.IngredientPagingRepository;
import cmahy.webapp.resource.impl.application.vo.input.PageableInputAppVo;
import cmahy.webapp.resource.impl.domain.taco.page.IngredientPage;
import cmahy.webapp.resource.taco.shop.vo.output.IngredientPageOutputVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAllIngredientPagedQueryTest {

    @Mock
    private IngredientPagingRepository ingredientPagingRepository;

    @Mock
    private IngredientPageOutputMapper ingredientPageOutputMapper;

    @InjectMocks
    private GetAllIngredientPagedQuery getAllIngredientPagedQuery;

    @Test
    void execute() {
        assertDoesNotThrow(() -> {
            PageableInputAppVo pageableInputAppVo = mock(PageableInputAppVo.class);
            IngredientPage ingredientPage = mock(IngredientPage.class);
            IngredientPageOutputVo ingredientPageOutputVo = mock(IngredientPageOutputVo.class);

            when(ingredientPagingRepository.findAll(pageableInputAppVo)).thenReturn(ingredientPage);
            when(ingredientPageOutputMapper.map(ingredientPage)).thenReturn(ingredientPageOutputVo);

            IngredientPageOutputVo actual = getAllIngredientPagedQuery.execute(pageableInputAppVo);

            assertThat(actual)
                .isNotNull()
                .isEqualTo(ingredientPageOutputVo);
        });
    }
}