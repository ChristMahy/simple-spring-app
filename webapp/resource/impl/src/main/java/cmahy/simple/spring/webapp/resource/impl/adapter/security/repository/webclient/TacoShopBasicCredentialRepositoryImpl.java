package cmahy.simple.spring.webapp.resource.impl.adapter.security.repository.webclient;

import cmahy.simple.spring.security.client.api.webclient.repository.BasicCredentialRepository;
import cmahy.simple.spring.security.client.api.webclient.vo.output.BasicCredentialOutputVo;
import cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.ApplicationProperties;
import cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.webclient.WebClientProperties;
import cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.webclient.tacoshop.TacoShopWebClientProperties;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TacoShopBasicCredentialRepositoryImpl implements BasicCredentialRepository {

    private final Optional<TacoShopWebClientProperties> tacoShopWebClientProperties;

    public TacoShopBasicCredentialRepositoryImpl(ApplicationProperties applicationProperties) {
        tacoShopWebClientProperties = Optional.ofNullable(applicationProperties)
            .map(ApplicationProperties::webClient)
            .map(WebClientProperties::tacoShop);
    }

    @Override
    public Optional<BasicCredentialOutputVo> getOne() {
        return tacoShopWebClientProperties
            .map(TacoShopWebClientProperties::basicAuthorizationCredentials)
            .map(credentials -> new BasicCredentialOutputVo(
                Optional.ofNullable(credentials.username()), Optional.ofNullable(credentials.password())
            ));
    }

}
