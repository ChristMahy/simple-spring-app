package cmahy.simple.spring.webapp.user.kernel.domain.builder;

import cmahy.simple.spring.webapp.user.kernel.domain.*;

import java.util.Collection;

public interface UserSecurityBuilder<US extends UserSecurity> {

    UserSecurityBuilder<US> userName(String userName);

    UserSecurityBuilder<US> password(byte[] password);

    UserSecurityBuilder<US> fullName(String fullName);

    UserSecurityBuilder<US> street(String street);

    UserSecurityBuilder<US> city(String city);

    UserSecurityBuilder<US> state(String state);

    UserSecurityBuilder<US> zip(String zip);

    UserSecurityBuilder<US> phoneNumber(String phoneNumber);

    <R extends Role> UserSecurityBuilder<US> roles(Collection<R> roles);

    UserSecurityBuilder<US> authProvider(AuthProvider authProvider);

    UserSecurityBuilder<US> expired(Boolean expired);

    UserSecurityBuilder<US> locked(Boolean locked);

    UserSecurityBuilder<US> enabled(Boolean enabled);

    UserSecurityBuilder<US> credentialsExpired(Boolean credentialsExpired);

    US build();
}
