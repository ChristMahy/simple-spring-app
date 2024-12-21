package cmahy.webapp.resource.impl.adapter.ui.error;

import cmahy.webapp.taco.shop.kernel.exception.UsageOnDeletionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ControllerAdvice(basePackages = { "cmahy.webapp.resource.impl.adapter.ui" })
public class GlobalUIErrorHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalUIErrorHandler.class);

    @ExceptionHandler
    public ModelAndView anyHandler(Throwable anyException, WebRequest request) {
        print(anyException);

        return wrap(anyException, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ DataAccessException.class, SQLException.class })
    public ModelAndView databaseExceptionHandler(Exception dbException, WebRequest request) {
        print(dbException);

        return this.wrap(new Exception("Access data error", dbException), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ UsageOnDeletionException.class })
    public ModelAndView usageOnDeletionExceptionHandler(
        UsageOnDeletionException usageOnDeletionException
    ) {
        print(usageOnDeletionException);

        return this.wrap(usageOnDeletionException, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler({ AuthenticationCredentialsNotFoundException.class })
    public ModelAndView authenticationExceptionHandler(
        AuthenticationCredentialsNotFoundException authenticationException
    ) {
        LOG.debug(authenticationException.getMessage(), authenticationException);

        return this.wrap(authenticationException, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({ AccessDeniedException.class })
    public ModelAndView accessDeniedExceptionHandler(
        AccessDeniedException accessDeniedException
    ) {
        LOG.debug(accessDeniedException.getMessage(), accessDeniedException);

        return this.wrap(accessDeniedException, HttpStatus.FORBIDDEN);
    }

    private ModelAndView wrap(Throwable throwable, HttpStatus httpStatus) {
        ModelAndView modelAndView = new ModelAndView("error");

        modelAndView.addObject("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        modelAndView.addObject("error", httpStatus.getReasonPhrase());
        modelAndView.addObject("status", httpStatus.value());
        modelAndView.addObject("message", throwable.getMessage());
        modelAndView.addObject("exception", throwable.getMessage());

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        throwable.printStackTrace(pw);

        modelAndView.addObject("trace", sw.toString());

        return modelAndView;
    }

    private void print(Throwable any) {
        LOG.error(any.getMessage(), any);
    }
}
