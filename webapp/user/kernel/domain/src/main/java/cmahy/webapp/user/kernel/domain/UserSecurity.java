package cmahy.webapp.user.kernel.domain;

public interface UserSecurity extends User {

    AuthProvider getAuthProvider();

    Boolean getExpired();

    Boolean getLocked();

    Boolean getEnabled();

    Boolean getCredentialsExpired();
}
