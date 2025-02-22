package cmahy.simple.spring.webapp.resource.impl.adapter.security.request.userinfo;

import cmahy.simple.spring.webapp.resource.impl.adapter.security.request.userinfo.vo.input.UserInfoRequestConfig;
import cmahy.simple.spring.webapp.resource.impl.adapter.security.vo.google.output.OAuth2UserInfo;

import java.util.Optional;

public interface GetUserInfoOauth2Service {

    Optional<OAuth2UserInfo> execute(UserInfoRequestConfig configs);
}
