package cmahy.webapp.resource.impl.adapter.api.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalErrorHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalErrorHandler.class);

    @ExceptionHandler
    public ResponseEntity<ProblemDetail> ioHandler(Throwable anyException, WebRequest request) {
        print(anyException);

        return this.wrap(
            ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, anyException.getMessage())
        );
    }

    private ResponseEntity<ProblemDetail> wrap(ProblemDetail problemDetail) {
        return ResponseEntity.of(problemDetail).build();
    }

    private void print(Throwable any) {
        LOG.error(any.getMessage(), any);
    }
}
