package cmahy.webapp.resource.impl.adapter.taco.shop.scheduler;

import cmahy.common.helper.Generator;
import cmahy.webapp.resource.impl.adapter.taco.shop.properties.ingredient.IngredientProperties;
import cmahy.webapp.resource.taco.shop.vo.output.IngredientOutputVo;
import cmahy.webapp.resource.taco.shop.vo.output.IngredientPageOutputVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientSchedulerTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private IngredientProperties ingredientProperties;

    @Mock
    private RestTemplate commonRestTemplate;

    @InjectMocks
    private IngredientScheduler ingredientScheduler;

    @Test
    void runGetAllQuery() {
        assertDoesNotThrow(() -> {
            String url = "http://" +
                Generator.generateAStringWithoutSpecialChars() + "." +
                Generator.generateAStringWithoutSpecialChars(3);

            Integer ingredientListSize = Generator.randomInt(500, 5000);

            IngredientPageOutputVo ingredientPageOutputVo = new IngredientPageOutputVo(
                Stream
                    .generate(() -> mock(IngredientOutputVo.class))
                    .limit(ingredientListSize)
                    .toList(),
                ingredientListSize.longValue()
            );

            when(ingredientProperties.externalResource().baseUrl()).thenReturn(url);
            when(commonRestTemplate.getForObject(anyString(), eq(IngredientPageOutputVo.class))).thenReturn(ingredientPageOutputVo);

            ingredientScheduler.runGetAllQuery();
        });
    }

    @Test
    void runGetAllQuery_isRunningThreadSafe() {
        assertDoesNotThrow(() -> {
            String url = "http://" +
                Generator.generateAStringWithoutSpecialChars() + "." +
                Generator.generateAStringWithoutSpecialChars(3);

            when(ingredientProperties.externalResource().baseUrl()).thenReturn(url);
            when(commonRestTemplate.getForObject(anyString(), eq(IngredientPageOutputVo.class))).thenThrow(RestClientException.class);

            ingredientScheduler.runGetAllQuery();
        });
    }
}