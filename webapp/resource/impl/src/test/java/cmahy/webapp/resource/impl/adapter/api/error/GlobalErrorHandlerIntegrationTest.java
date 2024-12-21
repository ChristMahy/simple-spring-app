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
                get(RestGlobalErrorHandlerTestPurposeApi.BASE_URL + RestGlobalErrorHandlerTestPurposeApi.NO_ERROR_URL)
                    .with(SecurityUserGenerator.generateRandomUser())
            )
                .andDo(print())
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect(result -> {
                    assertThat(result.getResponse().getContentAsString()).isEqualTo("should-not-be-an-error");
                });
        });
    }

    @Test
    void ioException_thenShouldBeA500WithDetails() {
        assertDoesNotThrow(() -> {
            mockMvc.perform(
                get(RestGlobalErrorHandlerTestPurposeApi.BASE_URL + RestGlobalErrorHandlerTestPurposeApi.IO_EXCEPTION_URL)
                    .with(SecurityUserGenerator.generateRandomUser())
            )
                .andDo(print())
                .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.detail").value("should-be-an-IO-exception"))
                .andExpect(jsonPath("$.instance").value(RestGlobalErrorHandlerTestPurposeApi.BASE_URL + RestGlobalErrorHandlerTestPurposeApi.IO_EXCEPTION_URL));
        });
    }

    @Test
    void exception_thenShouldBeA500WithDetails() {
        assertDoesNotThrow(() -> {
            mockMvc.perform(
                    get(RestGlobalErrorHandlerTestPurposeApi.BASE_URL + RestGlobalErrorHandlerTestPurposeApi.EXCEPTION_URL)
                        .with(SecurityUserGenerator.generateRandomUser())
                )
                .andDo(print())
                .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.detail").value("should-be-an-exception"))
                .andExpect(jsonPath("$.instance").value(RestGlobalErrorHandlerTestPurposeApi.BASE_URL + RestGlobalErrorHandlerTestPurposeApi.EXCEPTION_URL));
        });
    }

    @Test
    void runtimeException_thenShouldBeA500WithDetails() {
        assertDoesNotThrow(() -> {
            mockMvc.perform(
                get(RestGlobalErrorHandlerTestPurposeApi.BASE_URL + RestGlobalErrorHandlerTestPurposeApi.RUNTIME_EXCEPTION_URL)
                    .with(SecurityUserGenerator.generateRandomUser())
            )
                .andDo(print())
                .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.detail").value("should-be-an-runtime-exception"))
                .andExpect(jsonPath("$.instance").value(RestGlobalErrorHandlerTestPurposeApi.BASE_URL + RestGlobalErrorHandlerTestPurposeApi.RUNTIME_EXCEPTION_URL));
        });
    }

    @Test
    void sqlException_thenShouldBeA500WithDetails() {
        assertDoesNotThrow(() -> {
            mockMvc.perform(
                get(RestGlobalErrorHandlerTestPurposeApi.BASE_URL + RestGlobalErrorHandlerTestPurposeApi.SQL_EXCEPTION_URL)
                    .with(SecurityUserGenerator.generateRandomUser())
            )
                .andDo(print())
                .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.detail").value("Access data error"))
                .andExpect(jsonPath("$.instance").value(RestGlobalErrorHandlerTestPurposeApi.BASE_URL + RestGlobalErrorHandlerTestPurposeApi.SQL_EXCEPTION_URL));
        });
    }

    @Test
    void dataAccessException_thenShouldBeA500WithDetails() {
        assertDoesNotThrow(() -> {
            mockMvc.perform(
                get(RestGlobalErrorHandlerTestPurposeApi.BASE_URL + RestGlobalErrorHandlerTestPurposeApi.DATA_ACCESS_EXCEPTION_URL)
                    .with(SecurityUserGenerator.generateRandomUser())
            )
                .andDo(print())
                .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.detail").value("Access data error"))
                .andExpect(jsonPath("$.instance").value(RestGlobalErrorHandlerTestPurposeApi.BASE_URL + RestGlobalErrorHandlerTestPurposeApi.DATA_ACCESS_EXCEPTION_URL));
        });
    }

    @Test
    void usageOnDeletionException_thenShouldBeA406WithDetails() {
        assertDoesNotThrow(() -> {
            mockMvc.perform(
                    get(RestGlobalErrorHandlerTestPurposeApi.BASE_URL + RestGlobalErrorHandlerTestPurposeApi.USAGE_ON_DELETION_EXCEPTION_URL)
                        .with(SecurityUserGenerator.generateRandomUser())
                )
                .andDo(print())
                .andExpect(status().is(HttpStatus.NOT_ACCEPTABLE.value()))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.detail").value("should-be-an-usage-on-deletion-exception"))
                .andExpect(jsonPath("$.instance").value(RestGlobalErrorHandlerTestPurposeApi.BASE_URL + RestGlobalErrorHandlerTestPurposeApi.USAGE_ON_DELETION_EXCEPTION_URL));
        });
    }

    @Test
    void accessDeniedException_thenShouldBeA403WithDetails() {
        assertDoesNotThrow(() -> {
            mockMvc.perform(
                get(RestGlobalErrorHandlerTestPurposeApi.BASE_URL + RestGlobalErrorHandlerTestPurposeApi.ACCESS_DENIED_EXCEPTION_URL)
                    .with(SecurityUserGenerator.generateRandomUser())
            )
                .andDo(print())
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.detail").value(HttpStatus.FORBIDDEN.getReasonPhrase()))
                .andExpect(jsonPath("$.instance").value(RestGlobalErrorHandlerTestPurposeApi.BASE_URL + RestGlobalErrorHandlerTestPurposeApi.ACCESS_DENIED_EXCEPTION_URL));
        });
    }

    @Test
    void authenticationCredentialsNotFoundException_thenShouldBeA401WithDetails() {
        assertDoesNotThrow(() -> {
            mockMvc.perform(
                get(RestGlobalErrorHandlerTestPurposeApi.BASE_URL + RestGlobalErrorHandlerTestPurposeApi.AUTHENTICATION_CREDENTIALS_NOT_FOUND_EXCEPTION_URL)
                    .with(SecurityUserGenerator.generateRandomUser())
            )
                .andDo(print())
                .andExpect(status().is(HttpStatus.UNAUTHORIZED.value()))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.detail").value(HttpStatus.UNAUTHORIZED.getReasonPhrase()))
                .andExpect(jsonPath("$.instance").value(RestGlobalErrorHandlerTestPurposeApi.BASE_URL + RestGlobalErrorHandlerTestPurposeApi.AUTHENTICATION_CREDENTIALS_NOT_FOUND_EXCEPTION_URL));
        });
    }
}