package cmahy.webapp.resource.impl.adapter.ui.taco.shop;

import cmahy.webapp.resource.impl.adapter.ui.taco.shop.mapper.input.ClientOrderInputUiToAppMapper;
import cmahy.webapp.resource.impl.adapter.ui.taco.shop.mapper.input.TacoInputUiToAppMapper;
import cmahy.webapp.resource.impl.application.taco.shop.command.ReceiveNewClientOrderCommand;
import cmahy.webapp.resource.impl.application.taco.shop.vo.input.ClientOrderInputAppVo;
import cmahy.webapp.resource.impl.application.taco.shop.vo.input.TacoInputAppVo;
import cmahy.webapp.resource.impl.application.taco.shop.vo.output.ClientOrderOutputAppVo;
import cmahy.webapp.resource.impl.domain.user.id.UserId;
import cmahy.webapp.resource.impl.helper.security.user.SecurityUserGenerator;
import cmahy.webapp.resource.ui.taco.TacoUriConstant;
import cmahy.webapp.resource.ui.taco.vo.input.ClientOrderInputUiVo;
import cmahy.webapp.resource.ui.taco.vo.input.TacoInputUiVo;
import cmahy.webapp.resource.user.api.security.vo.output.UserSecurityDetails;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static cmahy.common.helper.Generator.generateAString;
import static cmahy.common.helper.Generator.randomInt;
import static cmahy.webapp.resource.ui.taco.shop.ClientOrderUi.TACOS;
import static cmahy.webapp.resource.ui.taco.shop.ClientOrderUi.TACO_ORDER_SESSION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ClientOrderUiImplIntegrationTest {

    @MockBean
    private ClientOrderInputUiToAppMapper clientOrderInputMapper;

    @MockBean
    private TacoInputUiToAppMapper tacoInputMapper;

    @MockBean
    private ReceiveNewClientOrderCommand receiveCommand;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void current_whenEmptyOrder_thenAnOrderShouldBePresentOnSessionAndEmpty() {
        assertDoesNotThrow(() -> {
            List<TacoInputUiVo> tacos = Stream.generate(() -> new TacoInputUiVo(
                    generateAString(), Collections.emptyList()
                ))
                .limit(randomInt(10, 50))
                .toList();

            mockMvc
                .perform(
                    get(TacoUriConstant.ClientOrder.CLIENT_ORDER_BASE_URL)
                        .sessionAttr(TACOS, tacos)
                        .with(SecurityUserGenerator.generateRandomUser())
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("taco-shop/client-order-form"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(result -> {
                    assertThat(result.getResponse()).isNotNull();

                    String contentAsString = result.getResponse().getContentAsString();

                    assertThat(contentAsString).isNotNull();

                    htmlClientOrderEmptyAssertion(contentAsString, tacos);
                })
                .andExpect(result -> {
                    HttpSession session = extractSessionWithAssertion(result);

                    Object actualClientOrderAsObject = session.getAttribute(TACO_ORDER_SESSION);

                    assertThat(actualClientOrderAsObject).isInstanceOf(ClientOrderInputUiVo.class);

                    ClientOrderInputUiVo clientOrder = (ClientOrderInputUiVo) actualClientOrderAsObject;

                    clientOrderEmptyAssertion(clientOrder);
                });
        });
    }

    @Test
    void current_whenPresentOrderWithValues_thenFormShouldBePrefilledWithTheseValues() {
        assertDoesNotThrow(() -> {
            List<TacoInputUiVo> tacos = Stream.generate(() -> new TacoInputUiVo(
                    generateAString(), Collections.emptyList()
                ))
                .limit(randomInt(10, 50))
                .toList();

            ClientOrderInputUiVo clientOrderInput = new ClientOrderInputUiVo(
                generateAString(),
                generateAString(),
                generateAString(),
                generateAString(),
                generateAString(),
                generateAString(),
                generateAString(),
                generateAString()
            );

            mockMvc
                .perform(
                    get(TacoUriConstant.ClientOrder.CLIENT_ORDER_BASE_URL)
                        .sessionAttr(TACOS, tacos)
                        .sessionAttr(TACO_ORDER_SESSION, clientOrderInput)
                        .with(SecurityUserGenerator.generateRandomUser())
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("taco-shop/client-order-form"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(result -> {
                    assertThat(result.getResponse()).isNotNull();

                    String contentAsString = result.getResponse().getContentAsString();

                    assertThat(contentAsString).isNotNull();

                    htmlAssertion(contentAsString, clientOrderInput, tacos);
                })
                .andExpect(result -> {
                    HttpSession session = extractSessionWithAssertion(result);

                    Object actualClientOrderAsObject = session.getAttribute(TACO_ORDER_SESSION);

                    assertThat(actualClientOrderAsObject).isInstanceOf(ClientOrderInputUiVo.class);

                    ClientOrderInputUiVo clientOrder = (ClientOrderInputUiVo) actualClientOrderAsObject;

                    clientOrderAssertion(clientOrder, clientOrderInput);
                });
        });
    }

    private void htmlClientOrderEmptyAssertion(
        String contentAsString,
        List<TacoInputUiVo> tacos
    ) {
        htmlAssertion(contentAsString, null, tacos);
    }

    private void htmlAssertion(
        String contentAsString,
        ClientOrderInputUiVo clientOrderInput,
        List<TacoInputUiVo> tacos
    ) {
        assertThat(contentAsString).contains("<title>Shopping cart - Spring app demo</title>");

        assertThat(contentAsString).matches("[\\s\\S]*<form[\\s\\S]*method=\"post\"[\\s\\S]*>[\\s\\S]*");
        assertThat(contentAsString).matches("[\\s\\S]*Deliver my taco masterpieces to...[\\s\\S]*");
        assertThat(contentAsString).matches("[\\s\\S]*<input[\\s\\S]*name=\"deliveryName\"[\\s\\S]*value=\"" + (clientOrderInput == null ? "" : Pattern.quote(clientOrderInput.deliveryName())) + "\"[\\s\\S]*/>[\\s\\S]*");
        assertThat(contentAsString).matches("[\\s\\S]*<input[\\s\\S]*name=\"deliveryStreet\"[\\s\\S]*value=\"" + (clientOrderInput == null ? "" : Pattern.quote(clientOrderInput.deliveryStreet())) + "\"[\\s\\S]*/>[\\s\\S]*");
        assertThat(contentAsString).matches("[\\s\\S]*<input[\\s\\S]*name=\"deliveryCity\"[\\s\\S]*value=\"" + (clientOrderInput == null ? "" : Pattern.quote(clientOrderInput.deliveryCity())) + "\"[\\s\\S]*/>[\\s\\S]*");
        assertThat(contentAsString).matches("[\\s\\S]*<input[\\s\\S]*name=\"deliveryState\"[\\s\\S]*value=\"" + (clientOrderInput == null ? "" : Pattern.quote(clientOrderInput.deliveryState())) + "\"[\\s\\S]*/>[\\s\\S]*");
        assertThat(contentAsString).matches("[\\s\\S]*<input[\\s\\S]*name=\"deliveryZip\"[\\s\\S]*value=\"" + (clientOrderInput == null ? "" : Pattern.quote(clientOrderInput.deliveryZip())) + "\"[\\s\\S]*/>[\\s\\S]*");
        assertThat(contentAsString).matches("[\\s\\S]*<input[\\s\\S]*name=\"ccNumber\"[\\s\\S]*value=\"" + (clientOrderInput == null ? "" : Pattern.quote(clientOrderInput.ccNumber())) + "\"[\\s\\S]*/>[\\s\\S]*");
        assertThat(contentAsString).matches("[\\s\\S]*<input[\\s\\S]*name=\"ccExpiration\"[\\s\\S]*value=\"" + (clientOrderInput == null ? "" : Pattern.quote(clientOrderInput.ccExpiration())) + "\"[\\s\\S]*/>[\\s\\S]*");
        assertThat(contentAsString).matches("[\\s\\S]*<input[\\s\\S]*name=\"ccCVV\"[\\s\\S]*value=\"" + (clientOrderInput == null ? "" : Pattern.quote(clientOrderInput.ccCVV())) + "\"[\\s\\S]*/>[\\s\\S]*");
        assertThat(contentAsString).matches("[\\s\\S]*<button[\\s\\S]*type=\"submit\"[\\s\\S]*>[\\s\\S]*");
        assertThat(contentAsString).matches("[\\s\\S]*<button[\\s\\S]*name=\"action\"[\\s\\S]*>[\\s\\S]*");
        assertThat(contentAsString).matches("[\\s\\S]*<button[\\s\\S]*value=\"another-taco\"[\\s\\S]*>[\\s\\S]*");
        assertThat(contentAsString).matches("[\\s\\S]*<button[\\s\\S]*value=\"process-order\"[\\s\\S]*>[\\s\\S]*");

        tacos.forEach(taco -> assertThat(contentAsString).matches("[\\s\\S]*<span>" + Pattern.quote(taco.name()) + "</span>[\\s\\S]*"));
    }

    @Test
    void current_onNoTacos_thenRedirectToDesignTacoPage() {
        assertDoesNotThrow(() -> {
            mockMvc
                .perform(
                    get(TacoUriConstant.ClientOrder.CLIENT_ORDER_BASE_URL)
                        .with(SecurityUserGenerator.generateRandomUser())
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(TacoUriConstant.Design.DESIGN_BASE_URL));
        });
    }

    @Test
    void current_onEmptyTacos_thenRedirectToDesignTacoPage() {
        assertDoesNotThrow(() -> {
            mockMvc
                .perform(
                    get(TacoUriConstant.ClientOrder.CLIENT_ORDER_BASE_URL)
                        .sessionAttr(TACOS, Collections.emptyList())
                        .with(SecurityUserGenerator.generateRandomUser())
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(TacoUriConstant.Design.DESIGN_BASE_URL));
        });
    }

    @Test
    void saveOrder_whenUserWantToAddTaco_thenKeepOnSessionOrderFieldsFilled() {
        assertDoesNotThrow(() -> {
            List<TacoInputUiVo> tacos = Stream.generate(() -> new TacoInputUiVo(
                    generateAString(), Collections.emptyList()
                ))
                .limit(randomInt(10, 50))
                .toList();

            ClientOrderInputUiVo clientOrderInput = new ClientOrderInputUiVo(
                generateAString(),
                generateAString(),
                generateAString(),
                generateAString(),
                generateAString(),
                generateAString(),
                generateAString(),
                generateAString()
            );

            mockMvc
                .perform(
                    post(TacoUriConstant.ClientOrder.CLIENT_ORDER_BASE_URL)
                        .with(SecurityUserGenerator.generateRandomUser())
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .sessionAttr(TACOS, tacos)
                        .param("action", "another-taco")
                        .param("deliveryName", clientOrderInput.deliveryName())
                        .param("deliveryState", clientOrderInput.deliveryState())
                        .param("deliveryStreet", clientOrderInput.deliveryStreet())
                        .param("deliveryCity", clientOrderInput.deliveryCity())
                        .param("deliveryZip", clientOrderInput.deliveryZip())
                        .param("ccNumber", clientOrderInput.ccNumber())
                        .param("ccExpiration", clientOrderInput.ccExpiration())
                        .param("ccCVV", clientOrderInput.ccCVV())
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(TacoUriConstant.Design.DESIGN_BASE_URL))
                .andExpect(result -> {
                    HttpSession session = extractSessionWithAssertion(result);

                    Object actualClientOrderAsObject = session.getAttribute(TACO_ORDER_SESSION);

                    assertThat(actualClientOrderAsObject).isInstanceOf(ClientOrderInputUiVo.class);

                    ClientOrderInputUiVo clientOrder = (ClientOrderInputUiVo) actualClientOrderAsObject;

                    clientOrderAssertion(clientOrder, clientOrderInput);
                })
                .andExpect(result -> verify(receiveCommand, never()).execute(any(ClientOrderInputAppVo.class), any(UserId.class)));
        });
    }

    @Test
    void saveOrder_completeOrder() {
        assertDoesNotThrow(() -> {
            UserSecurityDetails userSecurityDetails = SecurityUserGenerator.generateRandomUserDetails();

            List<TacoInputUiVo> tacoInputs = Stream.generate(() -> new TacoInputUiVo(
                    generateAString(), Collections.emptyList()
                ))
                .limit(randomInt(10, 50))
                .toList();

            List<TacoInputAppVo> tacos = tacoInputs.stream().map(t -> {
                TacoInputAppVo taco = mock(TacoInputAppVo.class);

                when(tacoInputMapper.map(t)).thenReturn(taco);

                return taco;
            }).toList();

            ClientOrderInputUiVo clientOrderInput = new ClientOrderInputUiVo(
                generateAString(),
                generateAString(),
                generateAString(),
                generateAString(),
                "5000",
                "1234123412341234",
                "08/24",
                "123"
            );

            ClientOrderInputAppVo clientOrderInputAppVo = mock(ClientOrderInputAppVo.class);
            UserId userId = new UserId(userSecurityDetails.userSecurity().id().value());
            ClientOrderOutputAppVo clientOrderOutputAppVo = mock(ClientOrderOutputAppVo.class);

            when(clientOrderInputMapper.map(clientOrderInput, tacos)).thenReturn(clientOrderInputAppVo);
            when(receiveCommand.execute(clientOrderInputAppVo, userId)).thenReturn(clientOrderOutputAppVo);

            mockMvc
                .perform(
                    post(TacoUriConstant.ClientOrder.CLIENT_ORDER_BASE_URL)
                        .with(SecurityUserGenerator.generateWithSpecificUser(userSecurityDetails))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .sessionAttr(TACOS, tacoInputs)
                        .param("action", "process-order")
                        .param("deliveryName", clientOrderInput.deliveryName())
                        .param("deliveryState", clientOrderInput.deliveryState())
                        .param("deliveryStreet", clientOrderInput.deliveryStreet())
                        .param("deliveryCity", clientOrderInput.deliveryCity())
                        .param("deliveryZip", clientOrderInput.deliveryZip())
                        .param("ccNumber", clientOrderInput.ccNumber())
                        .param("ccExpiration", clientOrderInput.ccExpiration())
                        .param("ccCVV", clientOrderInput.ccCVV())
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(TacoUriConstant.BASE_URL))
                .andExpect(result -> {
                    HttpSession session = extractSessionWithAssertion(result);

                    assertThat(session.getAttribute(TACO_ORDER_SESSION)).isNull();
                    assertThat(session.getAttribute(TACOS)).isNull();

                    verify(receiveCommand).execute(clientOrderInputAppVo, userId);
                });
        });
    }

    @Test
    void saveOrder_completeOrder_whenSomeFieldsOnError_thenStayOnSamePageAndShowFieldsWithError() {
        assertDoesNotThrow(() -> {
            List<TacoInputUiVo> tacoInputs = Stream.generate(() -> new TacoInputUiVo(
                    generateAString(), Collections.emptyList()
                ))
                .limit(randomInt(10, 50))
                .toList();

            ClientOrderInputUiVo clientOrderInput = new ClientOrderInputUiVo(
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                ""
            );

            mockMvc
                .perform(
                    post(TacoUriConstant.ClientOrder.CLIENT_ORDER_BASE_URL)
                        .with(SecurityUserGenerator.generateRandomUser())
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .sessionAttr(TACOS, tacoInputs)
                        .param("action", "process-order")
                        .param("deliveryName", clientOrderInput.deliveryName())
                        .param("deliveryState", clientOrderInput.deliveryState())
                        .param("deliveryStreet", clientOrderInput.deliveryStreet())
                        .param("deliveryCity", clientOrderInput.deliveryCity())
                        .param("deliveryZip", clientOrderInput.deliveryZip())
                        .param("ccNumber", clientOrderInput.ccNumber())
                        .param("ccExpiration", clientOrderInput.ccExpiration())
                        .param("ccCVV", clientOrderInput.ccCVV())
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpectAll(
                    model().hasErrors(),
                    model().attributeHasFieldErrors(
                        "tacoOrder",
                        "deliveryName",
                        "deliveryState",
                        "deliveryStreet",
                        "deliveryCity",
                        "deliveryZip",
                        "ccNumber",
                        "ccExpiration",
                        "ccCVV"
                    )
                )
                .andExpect(result -> {
                    HttpSession session = extractSessionWithAssertion(result);

                    assertThat(session.getAttribute(TACOS)).isEqualTo(tacoInputs);
                    htmlAssertion(result.getResponse().getContentAsString(), clientOrderInput, tacoInputs);

                    verify(receiveCommand, never()).execute(any(ClientOrderInputAppVo.class), any(UserId.class));
                });
        });
    }

    private void clientOrderEmptyAssertion(ClientOrderInputUiVo clientOrderSession) {
        assertThat(clientOrderSession.deliveryName()).isEmpty();
        assertThat(clientOrderSession.deliveryCity()).isEmpty();
        assertThat(clientOrderSession.deliveryState()).isEmpty();
        assertThat(clientOrderSession.deliveryStreet()).isEmpty();
        assertThat(clientOrderSession.deliveryZip()).isEmpty();
        assertThat(clientOrderSession.ccNumber()).isEmpty();
        assertThat(clientOrderSession.ccExpiration()).isEmpty();
        assertThat(clientOrderSession.ccCVV()).isEmpty();
    }

    private void clientOrderAssertion(
        ClientOrderInputUiVo clientOrderSession,
        ClientOrderInputUiVo clientOrder
    ) {
        assertThat(clientOrderSession.deliveryName()).isEqualTo(clientOrder.deliveryName());
        assertThat(clientOrderSession.deliveryCity()).isEqualTo(clientOrder.deliveryCity());
        assertThat(clientOrderSession.deliveryState()).isEqualTo(clientOrder.deliveryState());
        assertThat(clientOrderSession.deliveryStreet()).isEqualTo(clientOrder.deliveryStreet());
        assertThat(clientOrderSession.deliveryZip()).isEqualTo(clientOrder.deliveryZip());
        assertThat(clientOrderSession.ccNumber()).isEqualTo(clientOrder.ccNumber());
        assertThat(clientOrderSession.ccExpiration()).isEqualTo(clientOrder.ccExpiration());
        assertThat(clientOrderSession.ccCVV()).isEqualTo(clientOrder.ccCVV());
    }

    private HttpSession extractSessionWithAssertion(MvcResult result) {
        assertThat(result).isNotNull();
        assertThat(result.getRequest()).isNotNull();
        assertThat(result.getRequest().getSession()).isNotNull();

        return result.getRequest().getSession();
    }
}