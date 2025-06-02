package cmahy.simple.spring.webapp.shell.client.impl.adapter.repository.webclient;

import cmahy.simple.spring.security.client.api.webclient.repository.BasicCredentialRepository;
import cmahy.simple.spring.security.client.api.webclient.vo.output.BasicCredentialOutputVo;
import cmahy.simple.spring.webapp.shell.client.impl.adapter.config.properties.ApplicationProperties;
import cmahy.simple.spring.webapp.shell.client.impl.adapter.config.properties.webclient.WebClientProperties;
import cmahy.simple.spring.webapp.shell.client.impl.adapter.config.properties.webclient.user.UserWebClientProperties;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserBasicCredentialRepositoryImpl implements BasicCredentialRepository {

    private final Optional<UserWebClientProperties> webClientProperties;

    public UserBasicCredentialRepositoryImpl(ApplicationProperties applicationProperties) {
        this.webClientProperties = Optional.ofNullable(applicationProperties)
            .map(ApplicationProperties::webClient)
            .map(WebClientProperties::user);
    }

    @Override
    public Optional<BasicCredentialOutputVo> getOne() {
        return webClientProperties
            .map(UserWebClientProperties::basicAuthorizationCredentials)
            .map(credentials -> new BasicCredentialOutputVo(
                Optional.ofNullable(credentials.username()), Optional.ofNullable(credentials.password())
            ));
    }

}
