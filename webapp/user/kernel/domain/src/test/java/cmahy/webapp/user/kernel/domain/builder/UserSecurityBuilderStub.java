package cmahy.webapp.user.kernel.domain.builder;

import cmahy.webapp.user.kernel.domain.*;

import java.util.*;

public class UserSecurityBuilderStub implements UserSecurityBuilder<UserSecurityStub> {

    private Optional<UserSecurityStub> originalUserSecurity = Optional.empty();

    private String userName;
    private byte[] password;
    private String fullName;

    private String street;
    private String city;
    private String state;
    private String zip;

    private String phoneNumber;

    private Collection<RoleStub> roles;

    private AuthProvider authProvider;

    private Boolean expired;
    private Boolean locked;
    private Boolean enabled;
    private Boolean credentialsExpired;

    public UserSecurityBuilderStub() {}

    public UserSecurityBuilderStub(UserSecurityStub originalUserSecurity) {
        this.originalUserSecurity = Optional.ofNullable(originalUserSecurity);

        this.originalUserSecurity.ifPresent(userSecurity -> {
            this
                .userName(userSecurity.getUserName())
                .password(userSecurity.getPassword())
                .fullName(userSecurity.getFullName())
                .street(userSecurity.getStreet())
                .city(userSecurity.getCity())
                .state(userSecurity.getState())
                .zip(userSecurity.getZip())
                .phoneNumber(userSecurity.getPhoneNumber())
                .roles(userSecurity.getRoles())
                .authProvider(userSecurity.getAuthProvider())
                .expired(userSecurity.getExpired())
                .locked(userSecurity.getLocked())
                .enabled(userSecurity.getEnabled())
                .credentialsExpired(userSecurity.getCredentialsExpired());
        });
    }

    @Override
    public UserSecurityBuilderStub userName(String userName) {
        this.userName = userName;

        return this;
    }

    @Override
    public UserSecurityBuilderStub password(byte[] password) {
        this.password = password;

        return this;
    }

    @Override
    public UserSecurityBuilderStub fullName(String fullName) {
        this.fullName = fullName;

        return this;
    }

    @Override
    public UserSecurityBuilderStub street(String street) {
        this.street = street;

        return this;
    }

    @Override
    public UserSecurityBuilderStub city(String city) {
        this.city = city;

        return this;
    }

    @Override
    public UserSecurityBuilderStub state(String state) {
        this.state = state;

        return this;
    }

    @Override
    public UserSecurityBuilderStub zip(String zip) {
        this.zip = zip;

        return this;
    }

    @Override
    public UserSecurityBuilderStub phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;

        return this;
    }

    @Override
    public <R extends Role> UserSecurityBuilderStub roles(Collection<R> roles) {
        this.roles = (List<RoleStub>) roles;

        return this;
    }

    @Override
    public UserSecurityBuilderStub authProvider(AuthProvider authProvider) {
        this.authProvider = authProvider;

        return this;
    }

    @Override
    public UserSecurityBuilderStub expired(Boolean expired) {
        this.expired = expired;

        return this;
    }

    @Override
    public UserSecurityBuilderStub locked(Boolean locked) {
        this.locked = locked;

        return this;
    }

    @Override
    public UserSecurityBuilderStub enabled(Boolean enabled) {
        this.enabled = enabled;

        return this;
    }

    @Override
    public UserSecurityBuilderStub credentialsExpired(Boolean credentialsExpired) {
        this.credentialsExpired = credentialsExpired;

        return this;
    }

    @Override
    public UserSecurityStub build() {
        return this.originalUserSecurity
            .orElseGet(UserSecurityStub::new)
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
