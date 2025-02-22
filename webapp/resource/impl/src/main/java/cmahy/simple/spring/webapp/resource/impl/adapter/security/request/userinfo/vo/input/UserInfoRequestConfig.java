package cmahy.simple.spring.webapp.resource.impl.adapter.security.request.userinfo.vo.input;

import java.util.Optional;

public record UserInfoRequestConfig(
    Optional<String> accessToken
) {
}
