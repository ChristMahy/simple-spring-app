package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.id.converter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class UserGenericIdConverterFactoryTest {

    @Test
    void create() {
        assertDoesNotThrow(() -> {
            assertThat(UserGenericIdConverterFactory.create()).isNotNull();
        });
    }

}