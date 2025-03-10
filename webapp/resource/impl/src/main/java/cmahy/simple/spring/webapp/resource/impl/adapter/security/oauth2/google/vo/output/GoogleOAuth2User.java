package cmahy.simple.spring.webapp.resource.impl.adapter.security.oauth2.google.vo.output;

import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Optional;

public class GoogleOAuth2User {

    private final OAuth2User oAuth2User;

    public GoogleOAuth2User(OAuth2User oAuth2User) {
        this.oAuth2User = oAuth2User;
    }

    public OAuth2User oAuth2User() {
        return oAuth2User;
    }

    public Optional<String> id() {
        return Optional.ofNullable(this.oAuth2User.getAttribute("sub"));
    }

    public Optional<String> email() {
        return Optional.ofNullable(this.oAuth2User.getAttribute("email"));
    }

    public Optional<String> firstName() {
        return Optional.ofNullable(this.oAuth2User.getAttribute("given_name"));
    }

    public Optional<String> lastName() {
        return Optional.ofNullable(this.oAuth2User.getAttribute("family_name"));
    }
}
