package cmahy.brokers.consumer.rabbitmq.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.Queue;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class RabbitMQConfigurerTest {

    @InjectMocks
    private RabbitMQConfigurer configurer;

    @Test
    void jacksonMessageConverter() {
        assertDoesNotThrow(() -> {
            var objectMapper = mock(ObjectMapper.class);

            var actual = configurer.jacksonMessageConverter(objectMapper);

            assertThat(actual).isNotNull();
        });
    }

    @Test
    void messageModifyQueue() {
        assertDoesNotThrow(() -> {
            var actual = configurer.messageModifyQueue();

            assertThat(actual).isNotNull();
            assertThat(actual).isInstanceOf(Queue.class);
        });
    }

    @Test
    void messageDeleteQueue() {
        assertDoesNotThrow(() -> {
            var actual = configurer.messageDeleteQueue();

            assertThat(actual).isNotNull();
            assertThat(actual).isInstanceOf(Queue.class);
        });
    }
}