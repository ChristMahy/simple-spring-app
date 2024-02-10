//package cmahy.webapp.resource.impl.adapter.stream.service.zip;
//
//import cmahy.webapp.resource.impl.application.stream.service.zip.executor.ZipTaskExecutor;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.io.IOException;
//import java.io.OutputStream;
//import java.util.zip.ZipOutputStream;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class ZipServiceImplTest {
//
//    @InjectMocks
//    private ZipServiceImpl zipServiceImpl;
//
//    @Mock
//    private OutputStream outputStream;
//
//    @Mock
//    private ZipTaskExecutor zipTaskExecutor;
//
//    @Test
//    void execute() {
//        assertDoesNotThrow(() -> {
//            zipServiceImpl.execute(outputStream, zipTaskExecutor);
//
//            verify(zipTaskExecutor).execute(any(ZipOutputStream.class));
//        });
//    }
//
//    @Test
//    void execute_whenIOException_thenThrowException() {
//        var expectedException = mock(IOException.class);
//
//        var actual = assertThrows(IOException.class, () -> {
//            doThrow(expectedException).when(zipTaskExecutor).execute(any(ZipOutputStream.class));
//
//            zipServiceImpl.execute(outputStream, zipTaskExecutor);
//
//            verify(zipTaskExecutor).execute(any(ZipOutputStream.class));
//        });
//
//        assertThat(actual).isEqualTo(expectedException);
//    }
//}