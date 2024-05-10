package cmahy.webapp.resource.impl.adapter.ui;

import cmahy.common.helper.Generator;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.regex.Pattern;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.matchesRegex;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RegistrationApiImplIntegrationTest {

    @Inject
    private MockMvc mockMvc;

    @Test
    void registration() {
        assertDoesNotThrow(() -> {
            mockMvc
                .perform(get("/register"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("registration"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(content().string(containsString("<title>Registration - Spring app demo</title>")))
                .andExpect(content().string(matchesRegex(
                    "[\\s\\S]*<form[\\s\\S]*method=\"post\"[\\s\\S]*action=\"/register\"[\\s\\S]*id=\"registrationForm\"[\\s\\S]*>[\\s\\S]*"
                )));
        });
    }

    @Test
    void processRegistration() {
        assertDoesNotThrow(() -> {
            String password = Generator.generateAString();

            mockMvc
                .perform(
                    post("/register")
                        .with(csrf())
                        .param("username", Generator.generateAString())
                        .param("password", password)
                        .param("confirmPassword", password)
                        .param("fullName", Generator.generateAString())
                        .param("street", Generator.generateAString())
                        .param("city", Generator.generateAString())
                        .param("state", Generator.generateAString())
                        .param("zip", Generator.randomLong(99L, 99999L).toString())
                        .param("phone", Generator.generateAString())
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
        });
    }

    @Test
    void processRegistration_whenError_thenStayOnSameFormAndWarnUser() {
        assertDoesNotThrow(() -> {
            mockMvc
                .perform(
                    post("/register")
                        .with(csrf())
                        .param("username", Generator.generateAString())
                        .param("password", "          ")
                        .param("confirmPassword", Generator.generateAString())
                        .param("fullName", Generator.generateAString())
                        .param("street", Generator.generateAString())
                        .param("city", Generator.generateAString())
                        .param("state", Generator.generateAString())
                        .param("zip", Generator.generateAString())
                        .param("phone", Generator.generateAString())
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("registration"))
                .andExpect(content().string(containsString("registration.error.password.should-not-be-blank")))
                .andExpect(content().string(containsString("registration.error.zip.has-to-be-only-digit")))
                .andExpect(content().string(containsString("password.confirmation.failed")));
        });
    }
}