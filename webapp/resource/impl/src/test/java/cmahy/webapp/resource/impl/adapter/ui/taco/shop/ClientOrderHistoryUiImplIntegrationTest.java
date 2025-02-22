package cmahy.webapp.resource.impl.adapter.ui.taco.shop;

import cmahy.common.entity.page.EntityPageable;
import cmahy.webapp.resource.impl.helper.security.user.SecurityUserGenerator;
import cmahy.simple.spring.webapp.resource.ui.taco.TacoUriConstant;
import cmahy.webapp.taco.shop.kernel.application.query.GetAllClientOrderPagedQuery;
import cmahy.webapp.taco.shop.kernel.domain.IngredientType;
import cmahy.webapp.user.kernel.domain.id.UserId;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ClientOrderHistoryUiImplIntegrationTest {

    @MockBean
    private GetAllClientOrderPagedQuery getAllClientOrderPagedQuery;

    @Autowired
    private MockMvc mockMvc;

    private List<IngredientOutputVo> ingrediants;

    @BeforeEach
    void setUp() {
        this.ingrediants = generateSomeIngredients(1000);
    }

    private List<IngredientOutputVo> generateSomeIngredients(int limit) {
        return Stream.generate(() -> {
                UUID id = randomUUID();

                return new IngredientOutputVo(
                    new IngredientId(id),
                    id.toString(),
                    randomEnum(IngredientType.class).name()
                );
            })
            .limit(limit)
            .toList();
    }

    @Test
    void orders() {
        assertDoesNotThrow(() -> {
            List<ClientOrderOutputVo> clientOrders = generateSomeClientOrders(1000);
            ClientOrderPageOutputVo page = new ClientOrderPageOutputVo(
                Stream.generate(() -> clientOrders.get(randomInt(0, clientOrders.size() - 1)))
                    .distinct()
                    .limit(10)
                    .toList(),
                Integer.valueOf(clientOrders.size()).longValue()
            );

            when(getAllClientOrderPagedQuery.execute(any(UserId.class), any(EntityPageable.class))).thenReturn(page);

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
                    _ -> Pattern.matches(
                        "^[\\s\\S]*(" +
                            page.content().stream()
                                .map(clientOrder ->
                                    "(" + Pattern.quote(clientOrder.deliveryName()) + ")[\\s\\S]*(" +
                                        clientOrder.tacos().stream()
                                            .map(taco ->
                                                "(" + Pattern.quote(taco.name()) + ")[\\s\\S]*(" +
                                                    taco.ingredients().stream()
                                                        .map(ingredient ->
                                                            "(" + Pattern.quote(ingredient.name() + " - [" + ingredient.type() + "]") + ")"
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

    private List<ClientOrderOutputVo> generateSomeClientOrders(int limit) {
        return LongStream.rangeClosed(1, limit)
            .mapToObj(_ -> new ClientOrderOutputVo(
                new ClientOrderId(randomUUID()),
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

    private List<TacoOutputVo> generateSomeTacos(int limit) {
        return LongStream.rangeClosed(1, limit)
            .mapToObj(_ -> new TacoOutputVo(
                new TacoId(randomUUID()),
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