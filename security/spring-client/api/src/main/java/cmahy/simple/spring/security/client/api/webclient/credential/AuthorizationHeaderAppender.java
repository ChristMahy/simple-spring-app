package cmahy.simple.spring.security.client.api.webclient.credential;

import org.springframework.http.HttpHeaders;

public interface AuthorizationHeaderAppender {

    void execute(HttpHeaders headers);

}
