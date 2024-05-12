package cmahy.webapp.resource.impl.adapter.ui.taco.shop;

import cmahy.webapp.resource.impl.helper.security.user.SecurityUserGenerator;
import cmahy.webapp.resource.ui.taco.TacoUriConstant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class HomeUiImplIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void home() {
        assertDoesNotThrow(() -> {
            mockMvc
                .perform(
                    get(TacoUriConstant.BASE_URL)
                        .with(SecurityUserGenerator.generateRandomUser())
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("taco-shop/home"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(content().string(containsString("<h1>Taco's shop</h1>")))
                .andExpect(content().string(containsString("<title>Home - Spring app demo</title>")));
        });
    }
}