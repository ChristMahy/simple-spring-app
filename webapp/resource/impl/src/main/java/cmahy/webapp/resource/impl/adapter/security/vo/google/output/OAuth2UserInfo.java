package cmahy.webapp.resource.impl.adapter.security.vo.google.output;

import cmahy.webapp.resource.impl.domain.user.AuthProvider;

import java.util.*;

public interface OAuth2UserInfo {

    Map<String, Object> attributes();

    default <T> Optional<T> attribute(String name) {
        return Optional.ofNullable(Objects.nonNull(attributes()) ? ((T) attributes().get(name)) : null);
    }

    AuthProvider authProvider();

    default Optional<String> id() {
        return this.attribute("id");
    }

    default Optional<String> email() {
        return this.attribute("email");
    }

    default Optional<String> firstName() {
        return this.attribute("firstName");
    }

    default Optional<String> lastName() {
        return this.attribute("lastName");
    }
}
