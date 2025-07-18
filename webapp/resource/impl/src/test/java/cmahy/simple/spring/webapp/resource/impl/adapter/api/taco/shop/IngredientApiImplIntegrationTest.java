package cmahy.simple.spring.webapp.resource.impl.adapter.api.taco.shop;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.resource.api.taco.shop.IngredientApi;
import cmahy.simple.spring.webapp.resource.impl.adapter.security.local.vo.input.TacoResourceUserDetailsInputVo;
import cmahy.simple.spring.webapp.resource.impl.helper.security.user.SecurityUserGenerator;
import cmahy.simple.spring.webapp.taco.shop.kernel.application.repository.IngredientRepository;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.IngredientType;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.builder.factory.IngredientBuilderFactory;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.id.IngredientId;
import cmahy.simple.spring.webapp.taco.shop.kernel.vo.input.IngredientCreateInputVo;
import cmahy.simple.spring.webapp.taco.shop.kernel.vo.input.IngredientUpdateInputVo;
import cmahy.simple.spring.webapp.taco.shop.kernel.vo.output.IngredientOutputVo;
import cmahy.simple.spring.webapp.taco.shop.kernel.vo.output.IngredientPageOutputVo;
import cmahy.simple.spring.webapp.user.kernel.application.repository.UserSecurityRepository;
import cmahy.simple.spring.webapp.user.kernel.domain.AuthProvider;
import cmahy.simple.spring.webapp.user.kernel.domain.UserSecurity;
import cmahy.simple.spring.webapp.user.kernel.domain.id.*;
import cmahy.simple.spring.webapp.user.kernel.vo.output.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class IngredientApiImplIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserSecurityRepository userSecurityRepository;

    @Autowired
    private IngredientBuilderFactory ingredientBuilderFactory;

    @Test
    void create() {
        assertDoesNotThrow(() -> {

            IngredientCreateInputVo createInputVo = new IngredientCreateInputVo(
                Generator.generateAStringWithoutSpecialChars(20),
                Generator.randomEnum(IngredientType.class).name()
            );

            MvcResult postCreateResult = mockMvc.perform(
                    MockMvcRequestBuilders.post(IngredientApi.BASE_URL)
                        .content(objectMapper.writeValueAsString(createInputVo))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .with(getUser())
                )
                .andExpect(status().isCreated())
                .andReturn();

            IngredientOutputVo actual = objectMapper
                .readValue(postCreateResult.getResponse().getContentAsString(), IngredientOutputVo.class);

            assertThat(actual)
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .usingRecursiveComparison(
                    RecursiveComparisonConfiguration
                        .builder()
                        .withIgnoredFields("id")
                        .build()
                );

            Optional<? extends Ingredient> actualIngredient = ingredientRepository.findById(actual.id());

            assertThat(actualIngredient)
                .isNotNull()
                .isNotEmpty()
                .get()
                .hasNoNullFieldsOrProperties()
                .satisfies(ingredient -> {
                    assertThat(ingredient.getId()).isEqualTo(actual.id().value());
                    assertThat(ingredient.getName()).isEqualTo(createInputVo.name());
                    assertThat(ingredient.getType().name()).isEqualTo(createInputVo.type());
                });

        });
    }

    @Test
    void update() {
        assertDoesNotThrow(() -> {

            Ingredient ingredient = ingredientRepository.save(
                ingredientBuilderFactory.create()
                    .name(Generator.generateAString())
                    .type(Generator.randomEnum(IngredientType.class))
                    .build()
            );

            String newName = Generator.generateAString(20);
            String newType = Generator.randomEnum(IngredientType.class).name();

            MvcResult patchUpdateResult = mockMvc.perform(
                    MockMvcRequestBuilders.patch(IngredientApi.BASE_URL + "/{id}", ingredient.getId())
                        .content(objectMapper.writeValueAsString(
                            new IngredientUpdateInputVo(
                                Optional.of(newName),
                                Optional.of(newType)
                            )
                        ))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .with(getUser())
                )
                .andExpect(status().isOk())
                .andReturn();

            IngredientOutputVo actual = objectMapper
                .readValue(patchUpdateResult.getResponse().getContentAsString(), IngredientOutputVo.class);

            assertThat(actual)
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .satisfies(ingredientOutputVo -> {
                    assertThat(ingredientOutputVo.id().value()).isEqualTo(ingredient.getId());
                    assertThat(ingredientOutputVo.name()).isEqualTo(newName);
                    assertThat(ingredientOutputVo.type()).isEqualTo(newType);
                });

            Optional<? extends Ingredient> actualIngredient = ingredientRepository.findById(actual.id());

            assertThat(actualIngredient)
                .isNotNull()
                .isNotEmpty()
                .get()
                .hasNoNullFieldsOrProperties()
                .satisfies(ingredientResult -> {
                    assertThat(ingredientResult.getId()).isEqualTo(ingredient.getId());
                    assertThat(ingredientResult.getName()).isEqualTo(newName);
                    assertThat(ingredientResult.getType().name()).isEqualTo(newType);
                });

        });
    }

    @Test
    void delete() {
        assertDoesNotThrow(() -> {

            Ingredient ingredient = ingredientRepository.save(
                ingredientBuilderFactory.create()
                    .name(Generator.generateAString())
                    .type(Generator.randomEnum(IngredientType.class))
                    .build()
            );

            mockMvc.perform(
                    MockMvcRequestBuilders
                        .delete(IngredientApi.BASE_URL + "/{id}", ingredient.getId())
                        .with(getUser())
                )
                .andExpect(status().isNoContent())
                .andReturn();

            Optional<? extends Ingredient> actualIngredient = ingredientRepository.findById(new IngredientId(ingredient.getId()));

            assertThat(actualIngredient).isEmpty();

        });
    }

    @Test
    void getAll() {
        assertDoesNotThrow(() -> {

            MvcResult postCreateResult = mockMvc.perform(
                    MockMvcRequestBuilders.get(IngredientApi.BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .with(getUser())
                        .queryParam("page-number", "0")
                        .queryParam("page-size", String.valueOf(Integer.MAX_VALUE))
                )
                .andExpect(status().isOk())
                .andReturn();

            IngredientPageOutputVo actual = objectMapper
                .readValue(postCreateResult.getResponse().getContentAsString(), IngredientPageOutputVo.class);

            assertThat(actual).isNotNull();
            assertThat(actual.content()).hasSize(10);
            assertThat(actual.totalElements()).isEqualTo(10);
        });
    }

    private RequestPostProcessor getUser() {
        UserSecurity machine2machine = ((Optional<UserSecurity>) userSecurityRepository
            .findByUserNameAndAuthProvider("machine2machine", AuthProvider.LOCAL))
            .orElseThrow();

        return SecurityUserGenerator.generateWithSpecificUser(new TacoResourceUserDetailsInputVo(
            new UserSecurityOutputAppVo(
                new UserId(machine2machine.getId()),
                machine2machine.getUserName(),
                machine2machine.getPassword(),
                machine2machine.getFullName(),
                machine2machine.getStreet(),
                machine2machine.getCity(),
                machine2machine.getState(),
                machine2machine.getZip(),
                machine2machine.getPhoneNumber(),
                machine2machine.getAuthProvider(),
                machine2machine.getExpired(),
                machine2machine.getLocked(),
                machine2machine.getEnabled(),
                machine2machine.getCredentialsExpired(),
                machine2machine.getRoles().stream()
                    .map(r -> new RoleOutputAppVo(
                        new RoleId(r.getId()),
                        r.getName(),
                        r.getRights().stream()
                            .map(right -> new RightOutputAppVo(
                                new RightId(right.getId()),
                                right.getName()
                            ))
                            .collect(Collectors.toSet())
                    ))
                    .collect(Collectors.toSet()))
            )
        );
    }
}