package cmahy.brokers.consumer.core.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class JsonMapperConfigurerTest {

    @InjectMocks
    private JsonMapperConfigurer jsonMapperConfigurer;

    @Test
    void objectMapper() {
        var actual = jsonMapperConfigurer.objectMapper();

        assertThat(actual).isNotNull();
        assertThat(actual).isExactlyInstanceOf(ObjectMapper.class);
    }
}