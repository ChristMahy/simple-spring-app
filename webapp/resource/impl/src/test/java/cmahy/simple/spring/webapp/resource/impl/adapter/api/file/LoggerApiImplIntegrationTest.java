package cmahy.simple.spring.webapp.resource.impl.adapter.api.file;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.resource.api.file.LoggerApi;
import cmahy.simple.spring.webapp.resource.impl.application.file.repository.LoggerRepository;
import cmahy.simple.spring.webapp.resource.impl.helper.security.user.SecurityUserGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LoggerApiImplIntegrationTest {

    @MockitoBean
    private LoggerRepository loggerRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void logSomeText() {
        assertDoesNotThrow(() -> {
            String content = Generator.generateAString(10000);

            mockMvc
                .perform(
                    post(LoggerApi.BASE_URL)
                        .with(SecurityUserGenerator.generateRandomUser())
                        .contentType(MediaType.TEXT_PLAIN)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(content)
                )
                .andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(_ -> verify(loggerRepository).save(content));
        });
    }

    @Test
    void logSomeText_onContentNull_thenReturnA400() {
        assertDoesNotThrow(() -> {
            mockMvc
                .perform(
                    post(LoggerApi.BASE_URL)
                        .with(SecurityUserGenerator.generateRandomUser())
                        .contentType(MediaType.TEXT_PLAIN)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(_ -> verifyNoInteractions(loggerRepository));
        });
    }
}
