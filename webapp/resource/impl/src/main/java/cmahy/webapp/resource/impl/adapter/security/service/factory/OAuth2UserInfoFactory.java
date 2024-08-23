package cmahy.webapp.resource.impl.adapter.security.service.factory;

import cmahy.webapp.resource.impl.adapter.security.request.userinfo.GetUserInfoOauth2Service;
import cmahy.webapp.resource.impl.adapter.security.request.userinfo.vo.input.UserInfoRequestConfig;
import cmahy.webapp.resource.impl.adapter.security.vo.google.output.OAuth2UserInfo;
import cmahy.webapp.user.kernel.domain.AuthProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OAuth2UserInfoFactory {

    private static final Logger LOG = LoggerFactory.getLogger(OAuth2UserInfoFactory.class);

    private final GetUserInfoOauth2Service getUserInfoOauth2Service;

    public OAuth2UserInfoFactory(
        GetUserInfoOauth2Service getUserInfoOauth2Service
    ) {
        this.getUserInfoOauth2Service = getUserInfoOauth2Service;
    }

    public Optional<OAuth2UserInfo> create(OAuth2UserRequest request) {
        if (AuthProvider.GOOGLE.name().equalsIgnoreCase(request.getClientRegistration().getRegistrationId())) {
            return getUserInfoOauth2Service.execute(new UserInfoRequestConfig(
                Optional.ofNullable(request.getAccessToken().getTokenValue())
            ));
        }

        LOG.warn("Login with " + request.getClientRegistration().getRegistrationId() + " not supported");

        return Optional.empty();
    }
}
