package cmahy.simple.spring.webapp.resource.impl.application.stream.service.zip.factory;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.OutputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ZipProxyFactoryTest {

    @InjectMocks
    private ZipProxyFactory zipProxyFactory;

    @Test
    void create() {
        assertDoesNotThrow(() -> {
            var outputStream = mock(OutputStream.class);

            var actual = zipProxyFactory.create(outputStream);

            assertThat(actual).isNotNull();
        });
    }
}