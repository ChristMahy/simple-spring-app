package cmahy.webapp.resource.impl.adapter.ui.taco.shop;

import cmahy.webapp.resource.impl.application.taco.shop.properties.OrderProperties;
import cmahy.webapp.resource.impl.application.taco.shop.query.GetAllClientOrderPagedQuery;
import cmahy.webapp.resource.impl.application.taco.shop.vo.output.*;
import cmahy.webapp.resource.impl.application.vo.input.PageableInputAppVo;
import cmahy.webapp.resource.impl.domain.taco.*;
import cmahy.webapp.resource.impl.domain.taco.id.*;
import cmahy.webapp.resource.impl.helper.security.user.SecurityUserGenerator;
import cmahy.webapp.resource.ui.taco.TacoUriConstant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.*;

import static cmahy.common.helper.Generator.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ClientOrderHistoryApiImplIntegrationTest {

    @MockBean
    private GetAllClientOrderPagedQuery getAllClientOrderPagedQuery;

    @Autowired
    private MockMvc mockMvc;

    private List<IngredientOutputAppVo> ingrediants;

    @BeforeEach
    void setUp() {
        this.ingrediants = generateSomeIngredients(1000);
    }

    private List<IngredientOutputAppVo> generateSomeIngredients(int limit) {
        Set<String> ingredientIds = new HashSet<>(limit);

        return Stream.generate(() -> {
                String id;

                do {
                    id = generateAStringWithoutSpecialChars(5);
                } while (ingredientIds.contains(id));

                ingredientIds.add(id);

                return new IngredientOutputAppVo(
                    new IngredientId(id.toUpperCase()),
                    id.toUpperCase(),
                    randomEnum(Ingredient.Type.class).name()
                );
            })
            .limit(limit)
            .toList();
    }

    @Test
    void orders() {
        assertDoesNotThrow(() -> {
            List<ClientOrderOutputAppVo> clientOrders = generateSomeClientOrders(1000);
            ClientOrderPageOutputAppVo page = new ClientOrderPageOutputAppVo(
                Stream.generate(() -> clientOrders.get(randomInt(0, clientOrders.size() - 1)))
                    .distinct()
                    .limit(10)
                    .toList(),
                Integer.valueOf(clientOrders.size()).longValue()
            );

            when(getAllClientOrderPagedQuery.execute(any(PageableInputAppVo.class))).thenReturn(page);

            MvcResult requestResult = mockMvc.perform(
                    get(TacoUriConstant.ClientOrder.CLIENT_ORDER_BASE_URL + TacoUriConstant.ClientOrder.CLIENT_ORDER_HISTORY_URL)
                        .with(SecurityUserGenerator.generateRandomUser())
                        .queryParam("page-size", "10")
                        .queryParam("page-number", "0")
                )
                .andExpect(status().isOk())
                .andReturn();

            String contentAsString = requestResult.getResponse().getContentAsString();

            assertThat(contentAsString)
                .isNotBlank()
                .matches(
                    s -> Pattern.matches(
                        "^[\\s\\S]*(" +
                            page.content().stream()
                                .map(clientOrder ->
                                    "(" + Pattern.quote(clientOrder.deliveryName()) + ")[\\s\\S]*(" +
                                        clientOrder.tacos().stream()
                                            .map(taco ->
                                                "(" + Pattern.quote(taco.name()) + ")[\\s\\S]*(" +
                                                    taco.ingredients().stream()
                                                        .map(ingrediant ->
                                                            "(" + Pattern.quote(ingrediant.name() + " - [" + ingrediant.type() + "]") + ")"
                                                        )
                                                        .collect(Collectors.joining("[\\s\\S]*")) +
                                                    ")"
                                            )
                                            .collect(Collectors.joining("[\\s\\S]*")) +
                                    ")"
                                )
                                .collect(Collectors.joining("[\\s\\S]*")) +
                            ")[\\s\\S]*$",
                        contentAsString
                    ),
                    "Failed to match expected result"
                );
        });
    }

    private List<ClientOrderOutputAppVo> generateSomeClientOrders(int limit) {
        return LongStream.rangeClosed(1, limit)
            .mapToObj(index -> new ClientOrderOutputAppVo(
                new ClientOrderId(index),
                new Date(),
                generateAString(100),
                generateAString(100),
                generateAString(100),
                generateAString(100),
                generateAString(10),
                new StringBuffer()
                    .append(randomInt(1000, 10000))
                    .append(randomInt(1000, 10000))
                    .append(randomInt(1000, 10000))
                    .append(randomInt(1000, 10000))
                    .toString(),
                randomInt(1, 32) + "/" + randomInt(1, 13),
                String.valueOf(randomInt(100, 1000)),
                generateSomeTacos(randomInt(5, 10))
            ))
            .toList();
    }

    private List<TacoOutputAppVo> generateSomeTacos(int limit) {
        return LongStream.rangeClosed(1, limit)
            .mapToObj(index -> new TacoOutputAppVo(
                new TacoId(index),
                new Date(),
                generateAString(100),
                Stream.generate(() -> ingrediants.get(randomInt(0, ingrediants.size() - 1)))
                    .limit(randomInt(5, 10))
                    .distinct()
                    .toList()
            ))
            .toList();
    }
}