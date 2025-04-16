package cmahy.simple.spring.security.webclient.api.credential;

import org.springframework.http.HttpHeaders;

public interface AuthorizationHeaderAppender {

    void execute(HttpHeaders headers);

}
