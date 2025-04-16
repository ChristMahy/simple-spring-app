package cmahy.simple.spring.security.webclient.impl.credential;

import cmahy.simple.spring.security.webclient.api.credential.AuthorizationHeaderAppender;
import cmahy.simple.spring.security.webclient.api.repository.BasicCredentialRepository;
import cmahy.simple.spring.security.webclient.api.vo.output.BasicCredentialOutputVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class BasicAuthorizationHeaderAppenderImpl implements AuthorizationHeaderAppender {

    private static final String DEFAULT_USERNAME_OR_PASSWORD_VALUE = "";

    private final BasicCredentialRepository basicCredentialRepository;

    public BasicAuthorizationHeaderAppenderImpl(BasicCredentialRepository basicCredentialRepository) {
        this.basicCredentialRepository = basicCredentialRepository;
    }

    @Override
    public void execute(HttpHeaders headers) {
        Optional<BasicCredentialOutputVo> basicCredentials = basicCredentialRepository.getOne();

        basicCredentials
            .filter(bCredentials ->
                bCredentials.usernameAsString().map(StringUtils::isNotBlank).orElse(false) ||
                bCredentials.passwordAsString().map(StringUtils::isNotBlank).orElse(false)
            )
            .ifPresent(bCredentials -> {
                headers.setBasicAuth(username(bCredentials), password(bCredentials), StandardCharsets.UTF_8);
            });
    }

    private String username(BasicCredentialOutputVo basicCredential) {
        return basicCredential
            .usernameAsString()
            .filter(StringUtils::isNotBlank)
            .orElse(DEFAULT_USERNAME_OR_PASSWORD_VALUE);
    }

    private String password(BasicCredentialOutputVo basicCredential) {
        return basicCredential
            .passwordAsString()
            .filter(StringUtils::isNotBlank)
            .orElse(DEFAULT_USERNAME_OR_PASSWORD_VALUE);
    }
}
