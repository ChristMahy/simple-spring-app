package cmahy.springapp.resourceserver.config.security;

public final class SecurityConfigConstant {
    private SecurityConfigConstant() {}

    public static final String PROPERTY_PREFIX = "spring-app.security";

    public static final String DEFAULT_NAME_VALUE = "default";

    public static final String OAUTH2_NAME = "o-auth-2";
    public static final String OAUTH2_NAME_VALUE = "true";

    public static final String OAUTH2_PROPERTY_PREFIX = PROPERTY_PREFIX + ".oauth2";
}
