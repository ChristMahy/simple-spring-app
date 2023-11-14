package cmahy.brokers.consumer.jms.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class JmsConfigurerTest {

    @InjectMocks
    private JmsConfigurer configurer;

    @Test
    void mappingJackson2MessageConverter() {
        assertDoesNotThrow(() -> {
            var objectMapper = mock(ObjectMapper.class);

            MappingJackson2MessageConverter actual = configurer.mappingJackson2MessageConverter(objectMapper);

            assertThat(actual).isNotNull();
        });
    }
}