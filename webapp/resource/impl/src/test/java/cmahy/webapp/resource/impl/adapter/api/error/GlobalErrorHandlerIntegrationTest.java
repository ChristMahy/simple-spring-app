package cmahy.webapp.resource.impl.adapter.api.error;

import cmahy.webapp.resource.impl.helper.security.user.SecurityUserGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class GlobalErrorHandlerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void noError_thenShouldBeA200() {
        assertDoesNotThrow(() -> {
            mockMvc.perform(
                get(GlobalErrorHandlerTestPurposeApi.BASE_URL + GlobalErrorHandlerTestPurposeApi.NO_ERROR_URL)
                    .with(SecurityUserGenerator.generateRandomUser())
            )
                .andDo(print())
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    assertThat(result.getResponse().getContentAsString()).isEqualTo("should-not-be-an-error");
                });
        });
    }

    @Test
    void ioException_thenShouldBeA500WithDetails() {
        assertDoesNotThrow(() -> {
            mockMvc.perform(
                get(GlobalErrorHandlerTestPurposeApi.BASE_URL + GlobalErrorHandlerTestPurposeApi.IO_EXCEPTION_URL)
                    .with(SecurityUserGenerator.generateRandomUser())
            )
                .andDo(print())
                .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.detail").value("should-be-an-IO-exception"))
                .andExpect(jsonPath("$.instance").value(GlobalErrorHandlerTestPurposeApi.BASE_URL + GlobalErrorHandlerTestPurposeApi.IO_EXCEPTION_URL));
        });
    }

    @Test
    void exception_thenShouldBeA500WithDetails() {
        assertDoesNotThrow(() -> {
            mockMvc.perform(
                    get(GlobalErrorHandlerTestPurposeApi.BASE_URL + GlobalErrorHandlerTestPurposeApi.EXCEPTION_URL)
                        .with(SecurityUserGenerator.generateRandomUser())
                )
                .andDo(print())
                .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.detail").value("should-be-an-exception"))
                .andExpect(jsonPath("$.instance").value(GlobalErrorHandlerTestPurposeApi.BASE_URL + GlobalErrorHandlerTestPurposeApi.EXCEPTION_URL));
        });
    }

    @Test
    void runtimeException_thenShouldBeA500WithDetails() {
        assertDoesNotThrow(() -> {
            mockMvc.perform(
                get(GlobalErrorHandlerTestPurposeApi.BASE_URL + GlobalErrorHandlerTestPurposeApi.RUNTIME_EXCEPTION_URL)
                    .with(SecurityUserGenerator.generateRandomUser())
            )
                .andDo(print())
                .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.detail").value("should-be-an-runtime-exception"))
                .andExpect(jsonPath("$.instance").value(GlobalErrorHandlerTestPurposeApi.BASE_URL + GlobalErrorHandlerTestPurposeApi.RUNTIME_EXCEPTION_URL));
        });
    }

    @Test
    void sqlException_thenShouldBeA500WithDetails() {
        assertDoesNotThrow(() -> {
            mockMvc.perform(
                get(GlobalErrorHandlerTestPurposeApi.BASE_URL + GlobalErrorHandlerTestPurposeApi.SQL_EXCEPTION_URL)
                    .with(SecurityUserGenerator.generateRandomUser())
            )
                .andDo(print())
                .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.detail").value("Access data error"))
                .andExpect(jsonPath("$.instance").value(GlobalErrorHandlerTestPurposeApi.BASE_URL + GlobalErrorHandlerTestPurposeApi.SQL_EXCEPTION_URL));
        });
    }

    @Test
    void dataAccessException_thenShouldBeA500WithDetails() {
        assertDoesNotThrow(() -> {
            mockMvc.perform(
                get(GlobalErrorHandlerTestPurposeApi.BASE_URL + GlobalErrorHandlerTestPurposeApi.DATA_ACCESS_EXCEPTION_URL)
                    .with(SecurityUserGenerator.generateRandomUser())
            )
                .andDo(print())
                .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.detail").value("Access data error"))
                .andExpect(jsonPath("$.instance").value(GlobalErrorHandlerTestPurposeApi.BASE_URL + GlobalErrorHandlerTestPurposeApi.DATA_ACCESS_EXCEPTION_URL));
        });
    }
}