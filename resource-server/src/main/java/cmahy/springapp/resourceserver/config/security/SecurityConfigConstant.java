package cmahy.springapp.resourceserver.config.security;

public final class SecurityConfigConstant {
    private SecurityConfigConstant() {}

    static final String PROPERTY_PREFIX = "spring-app.security";

    static final String DEFAULT_NAME_VALUE = "default";

    static final String OAUTH2_NAME = "o-auth-2";
    static final String OAUTH2_NAME_VALUE = "true";

    static final String OAUTH2_PROPERTY_PREFIX = PROPERTY_PREFIX + ".oauth2";
}
