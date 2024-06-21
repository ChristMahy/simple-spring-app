package cmahy.webapp.resource.impl.adapter.api.taco.shop;

import cmahy.common.helper.Generator;
import cmahy.webapp.resource.impl.helper.security.user.SecurityUserGenerator;
import cmahy.webapp.resource.taco.shop.vo.input.IngredientCreateInputVo;
import cmahy.webapp.resource.taco.shop.vo.input.IngredientUpdateInputVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class IngredientApiImplSecurityIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAll_401() {
        assertDoesNotThrow(() -> {
            mockMvc.perform(
                get("/api/v1/ingredient")
                    .param("page-number", "0")
                    .param("page-size", "1000")
            )
                .andDo(print())
                .andExpect(status().isUnauthorized());
        });
    }

    @Test
    void getAll_403() {
        assertDoesNotThrow(() -> {
            mockMvc.perform(
                get("/api/v1/ingredient")
                    .with(SecurityUserGenerator.generateRandomUserWithSpecificRoles(new HashSet<>(0)))
                    .param("page-number", "0")
                    .param("page-size", "1000")
            )
                .andDo(print())
                .andExpect(status().isForbidden());
        });
    }

    @Test
    void create_401() {
        assertDoesNotThrow(() -> {
            IngredientCreateInputVo inputVo = new IngredientCreateInputVo(
                Generator.generateAString(),
                Generator.generateAString()
            );

            mockMvc.perform(
                    post("/api/v1/ingredient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(inputVo))
                )
                .andDo(print())
                .andExpect(status().isUnauthorized());
        });
    }

    @Test
    void create_403() {
        assertDoesNotThrow(() -> {
            IngredientCreateInputVo inputVo = new IngredientCreateInputVo(
                Generator.generateAString(),
                Generator.generateAString()
            );

            mockMvc.perform(
                    post("/api/v1/ingredient")
                        .with(
                            SecurityUserGenerator.generateRandomUserWithSpecificRoles(
                                SecurityUserGenerator.generateCommonApiRoles()
                            )
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(inputVo))
                )
                .andDo(print())
                .andExpect(status().isForbidden());
        });
    }

    @Test
    void update_401() {
        assertDoesNotThrow(() -> {
            IngredientUpdateInputVo inputVo = new IngredientUpdateInputVo(
                Optional.of(Generator.generateAString()),
                Optional.of(Generator.generateAString())
            );

            mockMvc.perform(
                    patch("/api/v1/ingredient/{id}", UUID.randomUUID().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(inputVo))
                )
                .andDo(print())
                .andExpect(status().isUnauthorized());
        });
    }

    @Test
    void update_403() {
        assertDoesNotThrow(() -> {
            IngredientUpdateInputVo inputVo = new IngredientUpdateInputVo(
                Optional.of(Generator.generateAString()),
                Optional.of(Generator.generateAString())
            );

            mockMvc.perform(
                    patch("/api/v1/ingredient/{id}", UUID.randomUUID().toString())
                        .with(
                            SecurityUserGenerator.generateRandomUserWithSpecificRoles(
                                SecurityUserGenerator.generateCommonApiRoles()
                            )
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(inputVo))
                )
                .andDo(print())
                .andExpect(status().isForbidden());
        });
    }

    @Test
    void delete_401() {
        assertDoesNotThrow(() -> {
            mockMvc.perform(
                    delete("/api/v1/ingredient/{id}", UUID.randomUUID().toString())
                )
                .andDo(print())
                .andExpect(status().isUnauthorized());
        });
    }

    @Test
    void delete_403() {
        assertDoesNotThrow(() -> {
            mockMvc.perform(
                    delete("/api/v1/ingredient/{id}", UUID.randomUUID().toString())
                        .with(
                            SecurityUserGenerator.generateRandomUserWithSpecificRoles(
                                SecurityUserGenerator.generateCommonApiRoles()
                            )
                        )
                )
                .andDo(print())
                .andExpect(status().isForbidden());
        });
    }
}