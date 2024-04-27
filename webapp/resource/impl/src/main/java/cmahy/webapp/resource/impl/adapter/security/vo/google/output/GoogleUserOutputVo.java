package cmahy.webapp.resource.impl.adapter.security.vo.google.output;

import cmahy.webapp.resource.impl.domain.user.AuthProvider;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Map;

public record GoogleUserOutputVo(
    Map<String, Object> attributes
) implements OAuth2UserInfo {

    public AuthProvider authProvider() {
        return AuthProvider.GOOGLE;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("id", id())
            .append("email", "...")
            .append("firstName", firstName())
            .append("lastName", lastName())
            .append("authProvider", authProvider())
            .append("oAuth2UserInfo", "...")
            .build();
    }
}
