//package cmahy.simple.spring.webapp.resource.impl.adapter.stream.service.zip;
//
//import cmahy.simple.spring.common.helper.Generator;
//import executor.zip.service.stream.application.cmahy.simple.spring.webapp.resource.impl.ZipSingleEntryTaskExecutor;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.io.IOException;
//import java.util.zip.ZipEntry;
//import java.util.zip.ZipOutputStream;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class ZipEntryServiceImplTest {
//
//    @InjectMocks
//    private ZipEntryServiceImpl zipEntryServiceImpl;
//
//    @Mock
//    private ZipOutputStream zipOutputStream;
//
//    private String entryName;
//
//    @Mock
//    private ZipSingleEntryTaskExecutor entryTask;
//
//    @BeforeEach
//    void setUp() {
//        entryName = Generator.generateAString();
//    }
//
//    @Test
//    void execute() {
//        assertDoesNotThrow(() -> {
//            try (var constructor = mockConstruction(ZipEntry.class)) {
//                zipEntryServiceImpl.execute(zipOutputStream, entryName, entryTask);
//
//                assertThat(constructor.constructed()).hasSize(1);
//
//                var actualZipEntry = constructor.constructed().get(0);
//                assertThat(actualZipEntry).isNotNull();
//
//                verify(zipOutputStream).putNextEntry(actualZipEntry);
//                verify(entryTask).execute(zipOutputStream);
//                verify(zipOutputStream).closeEntry();
//                verify(zipOutputStream).flush();
//
//                verifyNoMoreInteractions(zipOutputStream, entryTask);
//            }
//        });
//    }
//
//    @Test
//    void execute_whenIOError_thenThrowError() {
//        assertDoesNotThrow(() -> {
//            try (var constructor = mockConstruction(ZipEntry.class)) {
//                var ioExpected = mock(IOException.class);
//
//                var actual = assertThrows(IOException.class, () -> {
//                    doThrow(ioExpected).when(entryTask).execute(zipOutputStream);
//
//                    zipEntryServiceImpl.execute(zipOutputStream, entryName, entryTask);
//                });
//
//                assertThat(actual).isEqualTo(ioExpected);
//
//                assertThat(constructor.constructed()).hasSize(1);
//
//                var actualZipEntry = constructor.constructed().get(0);
//                assertThat(actualZipEntry).isNotNull();
//
//                verify(zipOutputStream).putNextEntry(actualZipEntry);
//                verify(entryTask).execute(zipOutputStream);
//
//                verifyNoMoreInteractions(zipOutputStream, entryTask);
//            }
//        });
//    }
//}