package cmahy.webapp.resource.impl.adapter.ui.taco.shop;

import cmahy.webapp.resource.impl.helper.security.user.SecurityUserGenerator;
import cmahy.webapp.resource.ui.taco.TacoUriConstant;
import cmahy.webapp.taco.shop.kernel.application.query.GetIngredientByTypeQuery;
import cmahy.webapp.taco.shop.kernel.domain.IngredientType;
import cmahy.webapp.taco.shop.kernel.domain.id.IngredientId;
import cmahy.webapp.taco.shop.kernel.vo.input.TacoInputVo;
import cmahy.webapp.taco.shop.kernel.vo.output.IngredientOutputVo;
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
import static cmahy.webapp.resource.ui.taco.shop.DesignUi.TACO;
import static cmahy.webapp.resource.ui.taco.shop.DesignUi.TACOS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class DesignUiImplIntegrationTest {

    @MockBean
    private GetIngredientByTypeQuery ingredientByTypeQuery;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        assertDoesNotThrow(() -> {
            for (IngredientType value : IngredientType.values()) {
                when(ingredientByTypeQuery.execute(value)).thenReturn(
                    Stream.generate(() -> new IngredientOutputVo(
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
                .perform(
                    get(TacoUriConstant.Design.DESIGN_BASE_URL)
                        .with(SecurityUserGenerator.generateRandomUser())
                )
                .andExpect(status().isOk())
                .andExpect(view().name("taco-shop/design"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(result -> {
                    HttpSession session = extractSessionWithAssertion(result);

                    assertThat(session.getAttribute(TACOS)).isNotNull();
                    assertThat((List<TacoInputVo>) session.getAttribute(TACOS)).isEmpty();

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
            List<TacoInputVo> tacos = Stream.generate(() -> new TacoInputVo(
                    generateAString(),
                    Collections.emptySet()
                ))
                .limit(randomInt(5, 10))
                .toList();

            mockMvc
                .perform(
                    get(TacoUriConstant.Design.DESIGN_BASE_URL)
                        .with(SecurityUserGenerator.generateRandomUser())
                        .sessionAttr(TACOS, tacos)
                )
                .andExpect(status().isOk())
                .andExpect(view().name("taco-shop/design"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(result -> {
                    HttpSession session = extractSessionWithAssertion(result);

                    assertThat(session.getAttribute(TACOS)).isNotNull();
                    assertThat((List<TacoInputVo>) session.getAttribute(TACOS)).containsExactlyElementsOf(tacos);

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
            List<TacoInputVo> tacos = Stream.generate(() -> new TacoInputVo(
                    generateAString(),
                    Stream.generate(() -> new IngredientId(generateAStringWithoutSpecialChars(10)))
                        .limit(randomInt(2, 5))
                        .collect(Collectors.toSet())
                ))
                .limit(randomInt(5, 10))
                .toList();

            TacoInputVo TacoInputVo = new TacoInputVo(
                generateAString(),
                Stream.generate(() -> new IngredientId(
                        generateAStringWithoutSpecialChars(10)
                    ))
                    .limit(randomInt(2, 10))
                    .collect(Collectors.toSet())
            );

            mockMvc
                .perform(
                    post(TacoUriConstant.Design.DESIGN_BASE_URL)
                        .with(SecurityUserGenerator.generateRandomUser())
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .sessionAttr(TACOS, new ArrayList<>(tacos))
                        .param("action", "add")
                        .param("name", TacoInputVo.name())
                        .param(
                            "ingredientIds",
                            TacoInputVo.ingredientIds().stream().map(IngredientId::value).toArray(String[]::new)
                        )
                )
                .andExpect(status().isOk())
                .andExpect(view().name("taco-shop/design"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(result -> {
                    HttpSession session = extractSessionWithAssertion(result);

                    assertThat(session.getAttribute(TACOS)).isNotNull();

                    List<TacoInputVo> tacosWithNewOne = new ArrayList<>(tacos);
                    tacosWithNewOne.add(TacoInputVo);
                    assertThat((List<TacoInputVo>) session.getAttribute(TACOS)).containsExactlyElementsOf(tacosWithNewOne);

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
            List<TacoInputVo> tacos = Stream.generate(() -> new TacoInputVo(
                    generateAString(),
                    Stream.generate(() -> new IngredientId(generateAStringWithoutSpecialChars(10)))
                        .limit(randomInt(2, 5))
                        .collect(Collectors.toSet())
                ))
                .limit(randomInt(5, 10))
                .toList();

            mockMvc
                .perform(
                    post(TacoUriConstant.Design.DESIGN_BASE_URL)
                        .with(SecurityUserGenerator.generateRandomUser())
                        .with(csrf())
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

                    assertThat((List<TacoInputVo>) session.getAttribute(TACOS)).containsExactlyElementsOf(tacos);
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
            List<TacoInputVo> tacos = Stream.generate(() -> new TacoInputVo(
                    generateAString(),
                    Stream.generate(() -> new IngredientId(generateAStringWithoutSpecialChars(10)))
                        .limit(randomInt(2, 5))
                        .collect(Collectors.toSet())
                ))
                .limit(randomInt(5, 10))
                .toList();

            TacoInputVo TacoInputVo = new TacoInputVo(
                generateAString(),
                Stream.generate(() -> new IngredientId(
                        generateAStringWithoutSpecialChars(10)
                    ))
                    .limit(randomInt(2, 10))
                    .collect(Collectors.toSet())
            );

            mockMvc
                .perform(
                    post(TacoUriConstant.Design.DESIGN_BASE_URL)
                        .with(SecurityUserGenerator.generateRandomUser())
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .sessionAttr(TACOS, new ArrayList<>(tacos))
                        .param("action", "create")
                        .param("name", TacoInputVo.name())
                        .param(
                            "ingredientIds",
                            TacoInputVo.ingredientIds().stream().map(IngredientId::value).toArray(String[]::new)
                        )
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(TacoUriConstant.ClientOrder.CLIENT_ORDER_BASE_URL))
                .andExpect(result -> {
                    HttpSession session = extractSessionWithAssertion(result);

                    assertThat(session.getAttribute(TACOS)).isNotNull();

                    List<TacoInputVo> tacosWithNewOne = new ArrayList<>(tacos);
                    tacosWithNewOne.add(TacoInputVo);
                    assertThat((List<TacoInputVo>) session.getAttribute(TACOS)).containsExactlyElementsOf(tacosWithNewOne);
                });
        });
    }

    @Test
    void saveDesignTaco_whenTacosIsEmpty_thenRedirectToTacoDesignPage() {
        assertDoesNotThrow(() -> {
            mockMvc
                .perform(
                    post(TacoUriConstant.Design.DESIGN_BASE_URL)
                        .with(SecurityUserGenerator.generateRandomUser())
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .sessionAttr(TACOS, new ArrayList<>())
                        .param("action", "create")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("taco-shop/design"))
                .andExpect(result -> {
                    HttpSession session = extractSessionWithAssertion(result);

                    assertThat(session.getAttribute(TACOS)).isNotNull();

                    assertThat((List<TacoInputVo>) session.getAttribute(TACOS)).isEmpty();
                });
        });
    }

    @Test
    void saveDesignTaco_whenTacoIsInvalidAndTacosIsNotEmpty_thenShouldNotAddToTacosSessionListButKeepGoingToOrderPage() {
        assertDoesNotThrow(() -> {

            List<TacoInputVo> tacos = Stream.generate(() -> new TacoInputVo(
                    generateAString(),
                    Stream.generate(() -> new IngredientId(generateAStringWithoutSpecialChars(10)))
                        .limit(randomInt(2, 5))
                        .collect(Collectors.toSet())
                ))
                .limit(randomInt(5, 10))
                .toList();

            mockMvc
                .perform(
                    post(TacoUriConstant.Design.DESIGN_BASE_URL)
                        .with(SecurityUserGenerator.generateRandomUser())
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .sessionAttr(TACOS, new ArrayList<>(tacos))
                        .param("action", "create")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(TacoUriConstant.ClientOrder.CLIENT_ORDER_BASE_URL))
                .andExpect(result -> {
                    HttpSession session = extractSessionWithAssertion(result);

                    assertThat(session.getAttribute(TACOS)).isNotNull();

                    assertThat((List<TacoInputVo>) session.getAttribute(TACOS)).containsExactlyElementsOf(tacos);
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