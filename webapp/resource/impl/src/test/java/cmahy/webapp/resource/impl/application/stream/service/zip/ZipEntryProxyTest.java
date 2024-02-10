package cmahy.webapp.resource.impl.application.stream.service.zip;

import cmahy.common.helper.Generator;
import cmahy.webapp.resource.impl.application.stream.exception.EmptyZipEntryException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.zip.ZipOutputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ZipEntryProxyTest {

    @Mock
    private ZipOutputStream zipStream;

    @Mock
    private ZipProxy zipProxy;

    @InjectMocks
    private ZipEntryProxy zipEntryProxy;

    private String zipEntryName;

    @BeforeEach
    void setUp() {
        zipEntryName = Generator.generateAString();

        lenient().when(zipProxy.outputStream()).thenReturn(zipStream);
    }

    @Test
    void currentEntry() {
        assertDoesNotThrow(() -> {
            zipEntryProxy.initializeEntry(zipEntryName);

            assertThat(zipEntryProxy.currentEntry()).isNotEmpty();
            assertThat(zipEntryProxy.currentEntry().get().getName()).isEqualTo(zipEntryName);
        });
    }

    @Test
    void initializeEntry() {
        assertDoesNotThrow(() -> {
            zipEntryProxy.initializeEntry(zipEntryName);

            verify(zipStream).putNextEntry(zipEntryProxy.currentEntry().get());
        });
    }

    @Test
    void appendToEntryContent() {
        assertDoesNotThrow(() -> {
            var bytes = Generator.randomBytes(Generator.randomInt(500, 2000));

            zipEntryProxy.initializeEntry(zipEntryName);

            zipEntryProxy.appendToEntryContent(bytes);

            verify(zipStream).write(bytes);
        });
    }

    @Test
    void appendToEntryContent_whenEntryIsEmpty_thenThrowEmptyZipEntryException() {
        assertDoesNotThrow(() -> {
            var bytes = Generator.randomBytes(Generator.randomInt(500, 2000));

            assertThrows(EmptyZipEntryException.class, () -> {
                zipEntryProxy.appendToEntryContent(bytes);
            });

            verify(zipStream, never()).write(bytes);
        });
    }

    @Test
    void close() {
        assertDoesNotThrow(() -> {
            zipEntryProxy.initializeEntry(zipEntryName);

            zipEntryProxy.close();

            verify(zipStream).closeEntry();
            verify(zipStream, never()).close();
        });
    }

    @Test
    void close_whenEntryIsEmpty_thenThrowEmptyZipEntryException() {
        assertDoesNotThrow(() -> {
            assertThrows(EmptyZipEntryException.class, () -> {
                zipEntryProxy.close();
            });

            verify(zipStream, never()).closeEntry();
        });
    }
}