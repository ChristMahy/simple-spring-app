package cmahy.simple.spring.webapp.resource.impl.adapter.ui.error;

import cmahy.simple.spring.webapp.resource.impl.helper.security.user.SecurityUserGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GlobalUIErrorHandlerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void noError_thenShouldBeA200() {
        assertDoesNotThrow(() -> {
            mockMvc.perform(
                    get(UIGlobalErrorHandlerTestPurposeApi.BASE_URL + UIGlobalErrorHandlerTestPurposeApi.NO_ERROR_URL)
                        .with(SecurityUserGenerator.generateRandomUser())
                )
                .andDo(print())
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(result -> {
                    assertThat(result.getResponse().getContentAsString()).contains("should-not-be-an-error");
                });
        });
    }

    @Test
    void ioException_thenShouldBeA500WithDetails() {
        assertDoesNotThrow(() -> {
            MvcResult requestResult = mockMvc.perform(
                    get(UIGlobalErrorHandlerTestPurposeApi.BASE_URL + UIGlobalErrorHandlerTestPurposeApi.IO_EXCEPTION_URL)
                        .with(SecurityUserGenerator.generateRandomUser())
                )
                .andDo(print())
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andReturn();

            resultContentAssertion(requestResult, HttpStatus.INTERNAL_SERVER_ERROR, "should-be-an-IO-exception");
        });
    }

    @Test
    void exception_thenShouldBeA500WithDetails() {
        assertDoesNotThrow(() -> {
            MvcResult requestResult = mockMvc.perform(
                    get(UIGlobalErrorHandlerTestPurposeApi.BASE_URL + UIGlobalErrorHandlerTestPurposeApi.EXCEPTION_URL)
                        .with(SecurityUserGenerator.generateRandomUser())
                )
                .andDo(print())
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andReturn();

            resultContentAssertion(requestResult, HttpStatus.INTERNAL_SERVER_ERROR, "should-be-an-exception");
        });
    }

    @Test
    void runtimeException_thenShouldBeA500WithDetails() {
        assertDoesNotThrow(() -> {
            MvcResult requestResult = mockMvc.perform(
                get(UIGlobalErrorHandlerTestPurposeApi.BASE_URL + UIGlobalErrorHandlerTestPurposeApi.RUNTIME_EXCEPTION_URL)
                    .with(SecurityUserGenerator.generateRandomUser())
            )
                .andDo(print())
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andReturn();

            resultContentAssertion(requestResult, HttpStatus.INTERNAL_SERVER_ERROR, "should-be-an-runtime-exception");
        });
    }

    @Test
    void sqlException_thenShouldBeA500WithDetails() {
        assertDoesNotThrow(() -> {
            MvcResult requestResult = mockMvc.perform(
                get(UIGlobalErrorHandlerTestPurposeApi.BASE_URL + UIGlobalErrorHandlerTestPurposeApi.SQL_EXCEPTION_URL)
                    .with(SecurityUserGenerator.generateRandomUser())
            )
                .andDo(print())
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andReturn();

            resultContentAssertion(requestResult, HttpStatus.INTERNAL_SERVER_ERROR, "Access data error");
        });
    }

    @Test
    void dataAccessException_thenShouldBeA500WithDetails() {
        assertDoesNotThrow(() -> {
            MvcResult requestResult = mockMvc.perform(
                get(UIGlobalErrorHandlerTestPurposeApi.BASE_URL + UIGlobalErrorHandlerTestPurposeApi.DATA_ACCESS_EXCEPTION_URL)
                    .with(SecurityUserGenerator.generateRandomUser())
            )
                .andDo(print())
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andReturn();

            resultContentAssertion(requestResult, HttpStatus.INTERNAL_SERVER_ERROR, "Access data error");
        });
    }

    @Test
    void usageOnDeletionException_thenShouldBeA406WithDetails() {
        assertDoesNotThrow(() -> {
            MvcResult requestResult = mockMvc.perform(
                    get(UIGlobalErrorHandlerTestPurposeApi.BASE_URL + UIGlobalErrorHandlerTestPurposeApi.USAGE_ON_DELETION_EXCEPTION_URL)
                        .with(SecurityUserGenerator.generateRandomUser())
            )
                .andDo(print())
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andReturn();

            resultContentAssertion(requestResult, HttpStatus.NOT_ACCEPTABLE, "should-be-an-usage-on-deletion-exception");
        });
    }

    @Test
    void accessDeniedException_thenShouldBeA403WithDetails() {
        assertDoesNotThrow(() -> {
            MvcResult requestResult = mockMvc.perform(
                get(UIGlobalErrorHandlerTestPurposeApi.BASE_URL + UIGlobalErrorHandlerTestPurposeApi.ACCESS_DENIED_EXCEPTION_URL)
                    .with(SecurityUserGenerator.generateRandomUser())
            )
                .andDo(print())
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andReturn();

            resultContentAssertion(requestResult, HttpStatus.FORBIDDEN, "should-be-an-access-denied-exception");
        });
    }

    @Test
    void authenticationCredentialsNotFoundException_thenShouldBeA401WithDetails() {
        assertDoesNotThrow(() -> {
            MvcResult requestResult = mockMvc.perform(
                get(UIGlobalErrorHandlerTestPurposeApi.BASE_URL + UIGlobalErrorHandlerTestPurposeApi.AUTHENTICATION_CREDENTIALS_NOT_FOUND_EXCEPTION_URL)
                    .with(SecurityUserGenerator.generateRandomUser())
            )
                .andDo(print())
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andReturn();

            resultContentAssertion(requestResult, HttpStatus.UNAUTHORIZED, "should-be-an-authentication-credentials-not-found-exception");
        });
    }

    private void resultContentAssertion(MvcResult requestResult, HttpStatus httpStatus, String message) throws UnsupportedEncodingException {
        String contentAsString = requestResult.getResponse().getContentAsString();

        assertThat(contentAsString)
            .isNotBlank()
            .matches("[\\s\\S]*<div class=\"error-content-row\">[\\s\\S]*<div>[\\s\\S]*<span>Error</span>[\\s\\S]*</div>[\\s\\S]*<div>[\\s\\S]*<span>" + Pattern.quote(httpStatus.getReasonPhrase()) + "</span>[\\s\\S]*</div>[\\s\\S]*</div>[\\s\\S]*")
            .matches("[\\s\\S]*<div class=\"error-content-row\">[\\s\\S]*<div>[\\s\\S]*<span>Status</span>[\\s\\S]*</div>[\\s\\S]*<div>[\\s\\S]*<span>" + httpStatus.value() + "</span>[\\s\\S]*</div>[\\s\\S]*</div>[\\s\\S]*")
            .matches("[\\s\\S]*<div class=\"error-content-row\">[\\s\\S]*<div>[\\s\\S]*<span>Message</span>[\\s\\S]*</div>[\\s\\S]*<div>[\\s\\S]*<span>" + Pattern.quote(message) + "</span>[\\s\\S]*</div>[\\s\\S]*</div>[\\s\\S]*")
            .matches("[\\s\\S]*<div class=\"error-content-row\">[\\s\\S]*<div>[\\s\\S]*<span>Exception</span>[\\s\\S]*</div>[\\s\\S]*<div>[\\s\\S]*<span>" + Pattern.quote(message) + "</span>[\\s\\S]*</div>[\\s\\S]*</div>[\\s\\S]*");
    }
}