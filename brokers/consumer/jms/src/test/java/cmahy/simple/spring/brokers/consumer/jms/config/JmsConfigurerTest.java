package cmahy.simple.spring.brokers.consumer.jms.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jms.support.converter.JacksonJsonMessageConverter;
import tools.jackson.databind.json.JsonMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class JmsConfigurerTest {

    @InjectMocks
    private JmsConfigurer configurer;

    @Test
    void mappingJackson2MessageConverter() {
        assertDoesNotThrow(() -> {
            var jsonMapper = mock(JsonMapper.class);

            JacksonJsonMessageConverter actual = configurer.mappingJackson2MessageConverter(jsonMapper);

            assertThat(actual).isNotNull();
        });
    }
}