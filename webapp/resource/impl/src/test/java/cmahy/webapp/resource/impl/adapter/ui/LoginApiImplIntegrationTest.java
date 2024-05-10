package cmahy.webapp.resource.impl.adapter.ui;

import cmahy.webapp.resource.impl.helper.security.user.SecurityUserGenerator;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.matchesRegex;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
class LoginApiImplIntegrationTest {

    @Inject
    private MockMvc mockMvc;

    @Test
    void login() {
        assertDoesNotThrow(() -> {
            mockMvc
                .perform(
                    get("/login")
                        .with(SecurityUserGenerator.generateRandomUser())
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(content().string(containsString("<title>Login - Spring app demo</title>")))
                .andExpect(content().string(matchesRegex(
                    "[\\s\\S]*<form[\\s\\S]*method=\"post\"[\\s\\S]*action=\"/login\"[\\s\\S]*id=\"loginForm\"[\\s\\S]*>[\\s\\S]*"
                )));
        });
    }
}