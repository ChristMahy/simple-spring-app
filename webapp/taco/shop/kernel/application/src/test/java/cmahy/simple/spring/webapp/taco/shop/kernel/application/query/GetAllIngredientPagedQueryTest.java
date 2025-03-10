package cmahy.simple.spring.webapp.taco.shop.kernel.application.query;

import cmahy.simple.spring.common.entity.page.EntityPageable;
import cmahy.simple.spring.webapp.taco.shop.kernel.application.mapper.output.IngredientPageOutputMapper;
import cmahy.simple.spring.webapp.taco.shop.kernel.application.repository.IngredientPagingRepository;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.page.IngredientPage;
import cmahy.simple.spring.webapp.taco.shop.kernel.vo.output.IngredientPageOutputVo;
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
    private IngredientPagingRepository<Ingredient> ingredientPagingRepository;

    @Mock
    private IngredientPageOutputMapper ingredientPageOutputMapper;

    @InjectMocks
    private GetAllIngredientPagedQuery getAllIngredientPagedQuery;

    @Test
    void execute() {
        assertDoesNotThrow(() -> {
            EntityPageable entityPageable = mock(EntityPageable.class);
            IngredientPage<Ingredient> ingredientPage = mock(IngredientPage.class);
            IngredientPageOutputVo ingredientPageOutputVo = mock(IngredientPageOutputVo.class);

            when(ingredientPagingRepository.findAll(entityPageable)).thenReturn(ingredientPage);
            when(ingredientPageOutputMapper.map(ingredientPage)).thenReturn(ingredientPageOutputVo);

            IngredientPageOutputVo actual = getAllIngredientPagedQuery.execute(entityPageable);

            assertThat(actual)
                .isNotNull()
                .isEqualTo(ingredientPageOutputVo);
        });
    }
}