package cmahy.simple.spring.brokers.consumer.core.adapter.api.error;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;
import java.util.List;

import static cmahy.simple.spring.common.helper.Generator.generateAString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class GlobalErrorHandlerTest {

    @InjectMocks
    private GlobalErrorHandler globalErrorHandler;

    @Test
    void ioHandler() {
        assertDoesNotThrow(() -> {
            var ioException = new IOException(generateAString());
            var webRequest = mock(WebRequest.class);

            var actual = globalErrorHandler.ioHandler(ioException, webRequest);

            assertThat(actual).isNotNull();
            assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);

            verifyNoInteractions(webRequest);
        });
    }

    @Test
    void exceptionHandler() {
        assertDoesNotThrow(() -> {
            var ioException = new Exception(generateAString());
            var webRequest = mock(WebRequest.class);

            var actual = globalErrorHandler.exceptionHandler(ioException, webRequest);

            assertThat(actual).isNotNull();
            assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
            assertThat(actual.getHeaders()).containsEntry(HttpHeaders.CONTENT_TYPE, List.of(MediaType.APPLICATION_PROBLEM_JSON_VALUE));

            assertThat(actual.getBody()).isNotNull();
            assertThat(actual.getBody().getDetail()).isEqualTo("Internal server error");
            assertThat(actual.getBody().getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());

            verifyNoInteractions(webRequest);
        });
    }

    @Test
    void runtimeExceptionHandler() {
        assertDoesNotThrow(() -> {
            var ioException = new RuntimeException(generateAString());
            var webRequest = mock(WebRequest.class);

            var actual = globalErrorHandler.exceptionHandler(ioException, webRequest);

            assertThat(actual).isNotNull();
            assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
            assertThat(actual.getHeaders()).containsEntry(HttpHeaders.CONTENT_TYPE, List.of(MediaType.APPLICATION_PROBLEM_JSON_VALUE));

            assertThat(actual.getBody()).isNotNull();
            assertThat(actual.getBody().getDetail()).isEqualTo("Internal server error");
            assertThat(actual.getBody().getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());

            verifyNoInteractions(webRequest);
        });
    }
}