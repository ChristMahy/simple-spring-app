package cmahy.simple.spring.webapp.resource.impl.adapter.ui;

import cmahy.simple.spring.common.helper.Generator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

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
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class RegistrationUiImplIntegrationTest {

    @Autowired
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
                        .formField("username", Generator.generateAString())
                        .formField("password", password)
                        .formField("confirmPassword", password)
                        .formField("fullName", Generator.generateAString())
                        .formField("street", Generator.generateAString())
                        .formField("city", Generator.generateAString())
                        .formField("state", Generator.generateAString())
                        .formField("zip", Generator.randomLong(99L, 99999L).toString())
                        .formField("phone", Generator.generateAString())
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
                        .formField("username", Generator.generateAString())
                        .formField("password", "          ")
                        .formField("confirmPassword", Generator.generateAString())
                        .formField("fullName", Generator.generateAString())
                        .formField("street", Generator.generateAString())
                        .formField("city", Generator.generateAString())
                        .formField("state", Generator.generateAString())
                        .formField("zip", Generator.generateAString())
                        .formField("phone", Generator.generateAString())
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