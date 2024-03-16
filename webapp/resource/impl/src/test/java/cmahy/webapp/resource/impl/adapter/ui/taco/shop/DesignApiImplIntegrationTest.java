package cmahy.webapp.resource.impl.adapter.ui.taco.shop;

import cmahy.webapp.resource.impl.application.taco.shop.query.GetIngredientByTypeQuery;
import cmahy.webapp.resource.impl.application.taco.shop.vo.output.IngredientOutputAppVo;
import cmahy.webapp.resource.impl.domain.taco.Ingredient;
import cmahy.webapp.resource.impl.domain.taco.id.IngredientId;
import cmahy.webapp.resource.ui.taco.TacoUriConstant;
import cmahy.webapp.resource.ui.taco.vo.id.IngredientApiId;
import cmahy.webapp.resource.ui.taco.vo.input.TacoInputApiVo;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static cmahy.common.helper.Generator.*;
import static cmahy.webapp.resource.ui.taco.shop.DesignApi.TACO;
import static cmahy.webapp.resource.ui.taco.shop.DesignApi.TACOS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class DesignApiImplIntegrationTest {

    @MockBean
    private GetIngredientByTypeQuery ingredientByTypeQuery;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        assertDoesNotThrow(() -> {
            for (Ingredient.Type value : Ingredient.Type.values()) {
                when(ingredientByTypeQuery.execute(value)).thenReturn(
                    Stream.generate(() -> new IngredientOutputAppVo(
                            new IngredientId(generateAStringWithoutSpecialChars(5)),
                            generateAStringWithoutSpecialChars(),
                            value.name()
                        ))
                        .limit(randomInt(2, 5))
                        .collect(Collectors.toSet())
                );
            }
        });
    }

    @Test
    void designForm_whenNoTacosInSession_thenCreateEmptyTacosList() {
        assertDoesNotThrow(() -> {
            mockMvc
                .perform(get(TacoUriConstant.Design.DESIGN_BASE_URL))
                .andExpect(status().isOk())
                .andExpect(view().name("taco-shop/design"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(result -> {
                    HttpSession session = extractSessionWithAssertion(result);

                    assertThat(session.getAttribute(TACOS)).isNotNull();
                    assertThat((List<TacoInputApiVo>) session.getAttribute(TACOS)).isEmpty();

                    String contentAsString = extractContentAsStringWithAssertion(result);

                    assertThat(contentAsString).matches("[\\s\\S]*<input.*id=\"taco-name\".*type=\"text\".*/>[\\s\\S]*");
                    assertThat(contentAsString).contains(
                        "Taco's shop",
                        "Design your taco !",
                        "Designate your wrap:",
                        "ick your protein:",
                        "Choose your cheese:",
                        "Determine your veggies:",
                        "Select your sauce:",
                        "Name your taco creation:"
                    );
                });
        });
    }

    @Test
    void designForm_whenTacosPresent_thenKeepItThroughRequest() {
        assertDoesNotThrow(() -> {
            List<TacoInputApiVo> tacos = Stream.generate(() -> new TacoInputApiVo(
                    generateAString(),
                    Collections.emptyList()
                ))
                .limit(randomInt(5, 10))
                .toList();

            mockMvc
                .perform(
                    get(TacoUriConstant.Design.DESIGN_BASE_URL)
                        .sessionAttr(TACOS, tacos)
                )
                .andExpect(status().isOk())
                .andExpect(view().name("taco-shop/design"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(result -> {
                    HttpSession session = extractSessionWithAssertion(result);

                    assertThat(session.getAttribute(TACOS)).isNotNull();
                    assertThat((List<TacoInputApiVo>) session.getAttribute(TACOS)).containsExactlyElementsOf(tacos);

                    String contentAsString = extractContentAsStringWithAssertion(result);

                    tacos.stream()
                        .map(t -> "[\\s\\S]*<li[\\s\\S]*>" + Pattern.quote(t.name()) + "</li>[\\s\\S]*")
                        .forEach(s -> assertThat(contentAsString).matches(s));
                });
        });
    }

    @Test
    void addDesignTaco() {
        assertDoesNotThrow(() -> {
            List<TacoInputApiVo> tacos = Stream.generate(() -> new TacoInputApiVo(
                    generateAString(),
                    Stream.generate(() -> new IngredientApiId(generateAStringWithoutSpecialChars(10)))
                        .limit(randomInt(2, 5))
                        .toList()
                ))
                .limit(randomInt(5, 10))
                .toList();

            TacoInputApiVo tacoInputApiVo = new TacoInputApiVo(
                generateAString(),
                Stream.generate(() -> new IngredientApiId(
                        generateAStringWithoutSpecialChars(10)
                    ))
                    .limit(randomInt(2, 10))
                    .toList()
            );

            mockMvc
                .perform(
                    post(TacoUriConstant.Design.DESIGN_BASE_URL)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .sessionAttr(TACOS, new ArrayList<>(tacos))
                        .param("action", "add")
                        .param("name", tacoInputApiVo.name())
                        .param(
                            "ingredientIds",
                            tacoInputApiVo.ingredientIds().stream().map(IngredientApiId::value).toArray(String[]::new)
                        )
                )
                .andExpect(status().isOk())
                .andExpect(view().name("taco-shop/design"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(result -> {
                    HttpSession session = extractSessionWithAssertion(result);

                    assertThat(session.getAttribute(TACOS)).isNotNull();

                    List<TacoInputApiVo> tacosWithNewOne = new ArrayList<>(tacos);
                    tacosWithNewOne.add(tacoInputApiVo);
                    assertThat((List<TacoInputApiVo>) session.getAttribute(TACOS)).containsExactlyElementsOf(tacosWithNewOne);

                    String contentAsString = extractContentAsStringWithAssertion(result);

                    tacosWithNewOne.stream()
                        .map(t -> "[\\s\\S]*<li[\\s\\S]*>" + Pattern.quote(t.name()) + "</li>[\\s\\S]*")
                        .forEach(s -> assertThat(contentAsString).matches(s));
                });
        });
    }

    @Test
    void addDesignTaco_whenTacoIsInvalid_thenShouldNotAddToTacosSessionListAndUIShowsErrorsToUser() {
        assertDoesNotThrow(() -> {
            List<TacoInputApiVo> tacos = Stream.generate(() -> new TacoInputApiVo(
                    generateAString(),
                    Stream.generate(() -> new IngredientApiId(generateAStringWithoutSpecialChars(10)))
                        .limit(randomInt(2, 5))
                        .toList()
                ))
                .limit(randomInt(5, 10))
                .toList();

            mockMvc
                .perform(
                    post(TacoUriConstant.Design.DESIGN_BASE_URL)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .sessionAttr(TACOS, new ArrayList<>(tacos))
                        .param("action", "add")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("taco-shop/design"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(result -> {
                    HttpSession session = extractSessionWithAssertion(result);

                    assertThat(session.getAttribute(TACOS)).isNotNull();

                    assertThat((List<TacoInputApiVo>) session.getAttribute(TACOS)).containsExactlyElementsOf(tacos);
                })
                .andExpectAll(
                    model().hasErrors(),
                    model().attributeHasFieldErrors(TACO, "name", "ingredientIds")
                );
        });
    }

    @Test
    void saveDesignTaco() {
        assertDoesNotThrow(() -> {
            List<TacoInputApiVo> tacos = Stream.generate(() -> new TacoInputApiVo(
                    generateAString(),
                    Stream.generate(() -> new IngredientApiId(generateAStringWithoutSpecialChars(10)))
                        .limit(randomInt(2, 5))
                        .toList()
                ))
                .limit(randomInt(5, 10))
                .toList();

            TacoInputApiVo tacoInputApiVo = new TacoInputApiVo(
                generateAString(),
                Stream.generate(() -> new IngredientApiId(
                        generateAStringWithoutSpecialChars(10)
                    ))
                    .limit(randomInt(2, 10))
                    .toList()
            );

            mockMvc
                .perform(
                    post(TacoUriConstant.Design.DESIGN_BASE_URL)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .sessionAttr(TACOS, new ArrayList<>(tacos))
                        .param("action", "create")
                        .param("name", tacoInputApiVo.name())
                        .param(
                            "ingredientIds",
                            tacoInputApiVo.ingredientIds().stream().map(IngredientApiId::value).toArray(String[]::new)
                        )
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(TacoUriConstant.ClientOrder.CLIENT_ORDER_BASE_URL))
                .andExpect(result -> {
                    HttpSession session = extractSessionWithAssertion(result);

                    assertThat(session.getAttribute(TACOS)).isNotNull();

                    List<TacoInputApiVo> tacosWithNewOne = new ArrayList<>(tacos);
                    tacosWithNewOne.add(tacoInputApiVo);
                    assertThat((List<TacoInputApiVo>) session.getAttribute(TACOS)).containsExactlyElementsOf(tacosWithNewOne);
                });
        });
    }

    @Test
    void saveDesignTaco_whenTacosIsEmpty_thenRedirectToTacoDesignPage() {
        assertDoesNotThrow(() -> {
            mockMvc
                .perform(
                    post(TacoUriConstant.Design.DESIGN_BASE_URL)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .sessionAttr(TACOS, new ArrayList<>())
                        .param("action", "create")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("taco-shop/design"))
                .andExpect(result -> {
                    HttpSession session = extractSessionWithAssertion(result);

                    assertThat(session.getAttribute(TACOS)).isNotNull();

                    assertThat((List<TacoInputApiVo>) session.getAttribute(TACOS)).isEmpty();
                });
        });
    }

    @Test
    void saveDesignTaco_whenTacoIsInvalidAndTacosIsNotEmpty_thenShouldNotAddToTacosSessionListButKeepGoingToOrderPage() {
        assertDoesNotThrow(() -> {

            List<TacoInputApiVo> tacos = Stream.generate(() -> new TacoInputApiVo(
                    generateAString(),
                    Stream.generate(() -> new IngredientApiId(generateAStringWithoutSpecialChars(10)))
                        .limit(randomInt(2, 5))
                        .toList()
                ))
                .limit(randomInt(5, 10))
                .toList();

            mockMvc
                .perform(
                    post(TacoUriConstant.Design.DESIGN_BASE_URL)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .sessionAttr(TACOS, new ArrayList<>(tacos))
                        .param("action", "create")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(TacoUriConstant.ClientOrder.CLIENT_ORDER_BASE_URL))
                .andExpect(result -> {
                    HttpSession session = extractSessionWithAssertion(result);

                    assertThat(session.getAttribute(TACOS)).isNotNull();

                    assertThat((List<TacoInputApiVo>) session.getAttribute(TACOS)).containsExactlyElementsOf(tacos);
                });
        });
    }

    private HttpSession extractSessionWithAssertion(MvcResult result) {
        assertThat(result).isNotNull();
        assertThat(result.getRequest()).isNotNull();
        assertThat(result.getRequest().getSession()).isNotNull();

        return result.getRequest().getSession();
    }

    private String extractContentAsStringWithAssertion(MvcResult result) throws UnsupportedEncodingException {
        assertThat(result).isNotNull();
        assertThat(result.getResponse()).isNotNull();

        return result.getResponse().getContentAsString();
    }
}