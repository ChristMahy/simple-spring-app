package cmahy.webapp.resource.impl.adapter.ui.error;

import cmahy.webapp.taco.shop.kernel.exception.UsageOnDeletionException;
import org.springframework.dao.DataAccessException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.sql.SQLException;

@Controller
@RequestMapping(path = UIGlobalErrorHandlerTestPurposeApi.BASE_URL)
public class UIGlobalErrorHandlerTestPurposeApi {

    public static final String BASE_URL = "/ui-global-error/test";

    public static final String NO_ERROR_URL = "/no-error";
    public static final String IO_EXCEPTION_URL = "/io-exception";
    public static final String EXCEPTION_URL = "/exception";
    public static final String RUNTIME_EXCEPTION_URL = "/runtime-exception";
    public static final String SQL_EXCEPTION_URL = "/sql-exception";
    public static final String DATA_ACCESS_EXCEPTION_URL = "/data-access-exception";
    public static final String USAGE_ON_DELETION_EXCEPTION_URL = "/usage-on-deletion-exception";
    public static final String ACCESS_DENIED_EXCEPTION_URL = "/access-denied-exception";
    public static final String AUTHENTICATION_CREDENTIALS_NOT_FOUND_EXCEPTION_URL = "/authentication-credentials-not-found-exception";

    @GetMapping(path = NO_ERROR_URL)
    public String noError() {
        return "should-not-be-an-error";
    }

    @GetMapping(path = IO_EXCEPTION_URL)
    public String throwAnIOException() throws IOException {
        throw new IOException("should-be-an-IO-exception");
    }

    @GetMapping(path = EXCEPTION_URL)
    public String throwAnException() throws Exception {
        throw new Exception("should-be-an-exception");
    }

    @GetMapping(path = RUNTIME_EXCEPTION_URL)
    public String throwARuntimeException() throws RuntimeException {
        throw new RuntimeException("should-be-an-runtime-exception");
    }

    @GetMapping(path = SQL_EXCEPTION_URL)
    public String throwSQLException() throws SQLException {
        throw new SQLException("should-be-an-sql-exception");
    }

    @GetMapping(path = DATA_ACCESS_EXCEPTION_URL)
    public String throwDataAccessException() throws DataAccessException {
        throw new DataAccessException("should-be-an-data-access-exception") {};
    }

    @GetMapping(path = USAGE_ON_DELETION_EXCEPTION_URL)
    public String throwUsageOnDeletionException() throws UsageOnDeletionException {
        throw new UsageOnDeletionException("should-be-an-usage-on-deletion-exception") {};
    }

    @GetMapping(path = ACCESS_DENIED_EXCEPTION_URL)
    public String throwAccessDeniedException() throws AccessDeniedException {
        throw new AccessDeniedException("should-be-an-access-denied-exception");
    }

    @GetMapping(path = AUTHENTICATION_CREDENTIALS_NOT_FOUND_EXCEPTION_URL)
    public String throwAuthenticationCredentialsNotFoundException() throws AuthenticationCredentialsNotFoundException {
        throw new AuthenticationCredentialsNotFoundException("should-be-an-authentication-credentials-not-found-exception");
    }
}
