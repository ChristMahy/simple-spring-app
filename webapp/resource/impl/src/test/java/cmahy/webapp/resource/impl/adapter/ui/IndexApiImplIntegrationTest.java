package cmahy.webapp.resource.impl.adapter.ui;

import cmahy.webapp.resource.impl.helper.security.user.SecurityUserGenerator;
import cmahy.webapp.resource.ui.IndexApi;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
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

@SpringBootTest
@AutoConfigureMockMvc
class IndexApiImplIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void index() {
        assertDoesNotThrow(() -> {
            mockMvc
                .perform(
                    get("/")
                        .with(SecurityUserGenerator.generateRandomUser())
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(content().string(containsString("<title>Index - Spring app demo</title>")))
                .andExpect(content().string(matchesRegex(
                    "[\\s\\S]*<h1><a href=\"/taco\">Taco's shop</a></h1>[\\s\\S]*<h1><a href=\"/without-controller\">Page without controller declaration</a></h1>[\\s\\S]*<h1><a href=\"/toggle-theme\">Toggle theme</a></h1>[\\s\\S]*")));
        });
    }

    @Test
    void toggleTheme() {
        assertDoesNotThrow(() -> {
            mockMvc
                .perform(
                    get("/" + IndexApi.TOGGLE_THEME)
                        .with(SecurityUserGenerator.generateRandomUser())
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andDo(result -> {
                    Cookie cookie = result.getResponse().getCookie("theme-mode");

                    assertThat(cookie).isNotNull();
                    assertThat(cookie.getValue()).isEqualTo("dark-theme");

                    mockMvc
                        .perform(
                            get(result.getResponse().getRedirectedUrl())
                                .cookie(cookie)
                                .with(SecurityUserGenerator.generateRandomUser())
                        )
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(view().name("index"))
                        .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                        .andExpect(content().string(containsString("<title>Index - Spring app demo</title>")))
                        .andExpect(content().string(containsString("<link rel=\"stylesheet\" href=\"/css/dark-theme/variables.css\"/>")));
                });
        });
    }
}