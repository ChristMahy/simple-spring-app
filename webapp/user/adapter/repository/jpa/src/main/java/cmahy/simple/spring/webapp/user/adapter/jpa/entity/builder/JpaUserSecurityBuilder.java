package cmahy.simple.spring.webapp.user.adapter.jpa.entity.builder;

import cmahy.simple.spring.webapp.user.adapter.jpa.entity.domain.JpaRole;
import cmahy.simple.spring.webapp.user.adapter.jpa.entity.domain.JpaUserSecurity;
import cmahy.simple.spring.webapp.user.kernel.domain.AuthProvider;
import cmahy.simple.spring.webapp.user.kernel.domain.Role;
import cmahy.simple.spring.webapp.user.kernel.domain.builder.UserSecurityBuilder;

import java.util.Collection;
import java.util.Optional;

public class JpaUserSecurityBuilder implements UserSecurityBuilder<JpaUserSecurity> {
    
    private Optional<JpaUserSecurity> originalUserSecurity = Optional.empty();

    private String userName;
    private byte[] password;
    private String fullName;

    private String street;
    private String city;
    private String state;
    private String zip;

    private String phoneNumber;

    private Collection<JpaRole> roles;

    private AuthProvider authProvider;

    private Boolean expired;
    private Boolean locked;
    private Boolean enabled;
    private Boolean credentialsExpired;

    public JpaUserSecurityBuilder() {}

    public JpaUserSecurityBuilder(JpaUserSecurity originalUserSecurity) {
        this.originalUserSecurity = Optional.ofNullable(originalUserSecurity);

        this.originalUserSecurity.ifPresent(user -> {
            this.userName(user.getUserName())
                .password(user.getPassword())
                .fullName(user.getFullName())
                .street(user.getStreet())
                .city(user.getCity())
                .state(user.getState())
                .zip(user.getZip())
                .phoneNumber(user.getPhoneNumber())
                .roles(user.getRoles());
        });
    }

    @Override
    public JpaUserSecurityBuilder userName(String userName) {
        this.userName = userName;

        return this;
    }

    @Override
    public JpaUserSecurityBuilder password(byte[] password) {
        this.password = password;

        return this;
    }

    @Override
    public JpaUserSecurityBuilder fullName(String fullName) {
        this.fullName = fullName;

        return this;
    }

    @Override
    public JpaUserSecurityBuilder street(String street) {
        this.street = street;

        return this;
    }

    @Override
    public JpaUserSecurityBuilder city(String city) {
        this.city = city;

        return this;
    }

    @Override
    public JpaUserSecurityBuilder state(String state) {
        this.state = state;

        return this;
    }

    @Override
    public JpaUserSecurityBuilder zip(String zip) {
        this.zip = zip;

        return this;
    }

    @Override
    public JpaUserSecurityBuilder phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;

        return this;
    }

    @Override
    public <R extends Role> JpaUserSecurityBuilder roles(Collection<R> roles) {
        this.roles = (Collection<JpaRole>) roles;

        return this;
    }

    @Override
    public JpaUserSecurityBuilder authProvider(AuthProvider authProvider) {
        this.authProvider = authProvider;

        return this;
    }

    @Override
    public JpaUserSecurityBuilder expired(Boolean expired) {
        this.expired = expired;

        return this;
    }

    @Override
    public JpaUserSecurityBuilder locked(Boolean locked) {
        this.locked = locked;

        return this;
    }

    @Override
    public JpaUserSecurityBuilder enabled(Boolean enabled) {
        this.enabled = enabled;

        return this;
    }

    @Override
    public JpaUserSecurityBuilder credentialsExpired(Boolean credentialsExpired) {
        this.credentialsExpired = credentialsExpired;

        return this;
    }

    @Override
    public JpaUserSecurity build() {
        return this.originalUserSecurity.orElseGet(JpaUserSecurity::new)
            .setUserName(this.userName)
            .setPassword(this.password)
            .setFullName(this.fullName)
            .setStreet(this.street)
            .setCity(this.city)
            .setState(this.state)
            .setZip(this.zip)
            .setPhoneNumber(this.phoneNumber)
            .setRoles(this.roles)
            .setAuthProvider(this.authProvider)
            .setExpired(this.expired)
            .setLocked(this.locked)
            .setEnabled(this.enabled)
            .setCredentialsExpired(this.credentialsExpired);
    }
}
