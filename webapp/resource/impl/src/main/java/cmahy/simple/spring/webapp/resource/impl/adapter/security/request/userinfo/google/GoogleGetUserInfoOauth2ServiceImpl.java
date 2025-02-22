package cmahy.simple.spring.webapp.resource.impl.adapter.security.request.userinfo.google;

import cmahy.simple.spring.webapp.resource.impl.adapter.security.config.properties.GoogleProperties;
import cmahy.simple.spring.webapp.resource.impl.adapter.security.request.userinfo.google.vo.output.GoogleProfileImpl;
import cmahy.simple.spring.webapp.resource.impl.adapter.security.request.userinfo.GetUserInfoOauth2Service;
import cmahy.simple.spring.webapp.resource.impl.adapter.security.request.userinfo.vo.input.UserInfoRequestConfig;
import cmahy.simple.spring.webapp.resource.impl.adapter.security.vo.google.output.GoogleUserOutputVo;
import cmahy.simple.spring.webapp.resource.impl.adapter.security.vo.google.output.OAuth2UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class GoogleGetUserInfoOauth2ServiceImpl implements GetUserInfoOauth2Service {

    private static final Logger LOG = LoggerFactory.getLogger(GoogleGetUserInfoOauth2ServiceImpl.class);

    private final GoogleProperties googleProperties;
    private final GoogleUserInfoRequestFactory googleUserInfoRequestFactory;

    public GoogleGetUserInfoOauth2ServiceImpl(
        GoogleProperties googleProperties,
        GoogleUserInfoRequestFactory googleUserInfoRequestFactory
    ) {
        this.googleProperties = googleProperties;
        this.googleUserInfoRequestFactory = googleUserInfoRequestFactory;
    }

    @Override
    public Optional<OAuth2UserInfo> execute(UserInfoRequestConfig configs) {
        RestTemplate restTemplate = googleUserInfoRequestFactory.create(configs);

        GoogleProfileImpl googleProfile = restTemplate.getForObject(googleProperties.apiUrl(), GoogleProfileImpl.class);

        if (Objects.isNull(googleProfile)) {
            return Optional.empty();
        }

        return Optional.of(new GoogleUserOutputVo(Map.of(
            "id", googleProfile.sub(),
            "name", googleProfile.name(),
            "firstName", googleProfile.given_name(),
            "lastName", googleProfile.family_name(),
            "email", googleProfile.email()
        )));
    }
}
