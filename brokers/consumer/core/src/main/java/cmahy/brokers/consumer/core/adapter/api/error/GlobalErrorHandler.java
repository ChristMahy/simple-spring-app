package cmahy.brokers.consumer.core.adapter.api.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;

@RestControllerAdvice
public class GlobalErrorHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalErrorHandler.class);



    @ExceptionHandler
    public ResponseEntity<Void> ioHandler(IOException ioException, WebRequest request) {
        print(ioException);

        return ResponseEntity
            .internalServerError()
            .build();
    }

    @ExceptionHandler
    public ResponseEntity<ProblemDetail> exceptionHandler(Exception exception, WebRequest request) {
        print(exception);

        return ResponseEntity
            .internalServerError()
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PROBLEM_JSON_VALUE)
            .body(
                ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error")
            );
    }

    @ExceptionHandler
    public ResponseEntity<ProblemDetail> exceptionHandler(RuntimeException exception, WebRequest request) {
        print(exception);

        return ResponseEntity
            .internalServerError()
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PROBLEM_JSON_VALUE)
            .body(
                ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error")
            );
    }

    private void print(Throwable any) {
        LOG.error(any.getMessage(), any);
    }
}
