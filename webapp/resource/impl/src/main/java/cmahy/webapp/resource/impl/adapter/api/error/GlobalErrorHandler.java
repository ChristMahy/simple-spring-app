package cmahy.webapp.resource.impl.adapter.api.error;

import cmahy.webapp.taco.shop.kernel.exception.UsageOnDeletionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice(annotations = RestController.class)
public class GlobalErrorHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalErrorHandler.class);

    @ExceptionHandler
    public ResponseEntity<ProblemDetail> anyHandler(Throwable anyException, WebRequest request) {
        print(anyException);

        return this.wrap(
            ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, anyException.getMessage())
        );
    }

    @ExceptionHandler({ DataAccessException.class, SQLException.class })
    public ResponseEntity<ProblemDetail> databaseExceptionHandler(Exception dbException, WebRequest request) {
        print(dbException);

        return this.wrap(
            ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Access data error")
        );
    }

    @ExceptionHandler({ UsageOnDeletionException.class })
    public ResponseEntity<ProblemDetail> usageOnDeletionExceptionHandler(
        UsageOnDeletionException usageOnDeletionException
    ) {
        print(usageOnDeletionException);

        return this.wrap(ProblemDetail.forStatusAndDetail(
            HttpStatus.NOT_ACCEPTABLE,
            usageOnDeletionException.getMessage()
        ));
    }

    @ExceptionHandler({ AuthenticationCredentialsNotFoundException.class })
    public ResponseEntity<ProblemDetail> authenticationExceptionHandler(
        AuthenticationCredentialsNotFoundException authenticationException
    ) {
        print(authenticationException);

        return this.wrap(ProblemDetail.forStatusAndDetail(
            HttpStatus.UNAUTHORIZED,
            HttpStatus.UNAUTHORIZED.getReasonPhrase()
        ));
    }

    @ExceptionHandler({ AccessDeniedException.class })
    public ResponseEntity<ProblemDetail> accessDeniedExceptionHandler(
        AccessDeniedException accessDeniedException
    ) {
        print(accessDeniedException);

        return this.wrap(ProblemDetail.forStatusAndDetail(
            HttpStatus.FORBIDDEN,
            HttpStatus.FORBIDDEN.getReasonPhrase()
        ));
    }

    private ResponseEntity<ProblemDetail> wrap(ProblemDetail problemDetail) {
        return ResponseEntity.of(problemDetail).build();
    }

    private void print(Throwable any) {
        LOG.error(any.getMessage(), any);
    }
}
