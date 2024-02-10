package cmahy.webapp.resource.impl.application.stream.service.zip;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.OutputStream;
import java.util.zip.ZipOutputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ZipProxyTest {

    @Mock
    private OutputStream outputStream;

    @InjectMocks
    private ZipProxy zipProxy;

    @Test
    void outputStream() {
        assertDoesNotThrow(() -> {
            assertThat(zipProxy.outputStream())
                .isNotNull()
                .isInstanceOf(ZipOutputStream.class);
        });
    }

    @Test
    void close() {
        assertDoesNotThrow(() -> {
            zipProxy.close();

            verify(outputStream).close();
        });
    }
}