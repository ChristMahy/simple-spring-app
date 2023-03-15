package cmahy.springapp.resourceserver.config.security;

public final class HttpSecurityConfig {
    public static final int ORDER_CONFIG_NORMAL_LOGIN = 1;
    public static final int ORDER_CONFIG_OAUTH2_LOGIN = 2;
    public static final int ORDER_CONFIG_LOGOUT = 3;

    private HttpSecurityConfig() {}
}
