package cmahy.webapp.user.adapter.cassandra.entity.builder;

import cmahy.webapp.user.adapter.cassandra.entity.domain.CassandraUserSecurityImpl;
import cmahy.webapp.user.adapter.cassandra.entity.proxy.CassandraRoleProxy;
import cmahy.webapp.user.adapter.cassandra.entity.proxy.CassandraUserSecurityProxy;
import cmahy.webapp.user.adapter.cassandra.entity.proxy.factory.CassandraUserSecurityProxyFactory;
import cmahy.webapp.user.kernel.domain.AuthProvider;
import cmahy.webapp.user.kernel.domain.Role;
import cmahy.webapp.user.kernel.domain.builder.UserSecurityBuilder;

import java.util.*;

public class CassandraUserSecurityBuilder implements UserSecurityBuilder<CassandraUserSecurityProxy> {

    private final CassandraUserSecurityProxyFactory userSecurityProxyFactory;
    private Optional<CassandraUserSecurityProxy> originalUserSecurity = Optional.empty();

    private String userName;
    private byte[] password;
    private String fullName;

    private String street;
    private String city;
    private String state;
    private String zip;

    private String phoneNumber;

    private Set<CassandraRoleProxy> roles;

    private AuthProvider authProvider;
    private Boolean expired;
    private Boolean locked;
    private Boolean enabled;
    private Boolean credentialsExpired;

    public CassandraUserSecurityBuilder(CassandraUserSecurityProxyFactory userSecurityProxyFactory) {
        this.userSecurityProxyFactory = userSecurityProxyFactory;
    }

    public CassandraUserSecurityBuilder(
        CassandraUserSecurityProxyFactory userSecurityProxyFactory,
        CassandraUserSecurityProxy originalUserSecurity
    ) {
        this(userSecurityProxyFactory);

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
                .roles(user.getRoles())
                .authProvider(user.getAuthProvider())
                .enabled(user.getEnabled())
                .expired(user.getExpired())
                .locked(user.getLocked())
                .credentialsExpired(user.getCredentialsExpired());
        });
    }

    @Override
    public UserSecurityBuilder<CassandraUserSecurityProxy> userName(String userName) {
        this.userName = userName;
        return this;
    }

    @Override
    public UserSecurityBuilder<CassandraUserSecurityProxy> password(byte[] password) {
        this.password = password;
        return this;
    }

    @Override
    public UserSecurityBuilder<CassandraUserSecurityProxy> fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    @Override
    public UserSecurityBuilder<CassandraUserSecurityProxy> street(String street) {
        this.street = street;
        return this;
    }

    @Override
    public UserSecurityBuilder<CassandraUserSecurityProxy> city(String city) {
        this.city = city;
        return this;
    }

    @Override
    public UserSecurityBuilder<CassandraUserSecurityProxy> state(String state) {
        this.state = state;
        return this;
    }

    @Override
    public UserSecurityBuilder<CassandraUserSecurityProxy> zip(String zip) {
        this.zip = zip;
        return this;
    }

    @Override
    public UserSecurityBuilder<CassandraUserSecurityProxy> phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    @Override
    public <R extends Role> UserSecurityBuilder<CassandraUserSecurityProxy> roles(Collection<R> roles) {
        this.roles = (Set<CassandraRoleProxy>) roles;
        return this;
    }

    @Override
    public UserSecurityBuilder<CassandraUserSecurityProxy> authProvider(AuthProvider authProvider) {
        this.authProvider = authProvider;
        return this;
    }

    @Override
    public UserSecurityBuilder<CassandraUserSecurityProxy> expired(Boolean expired) {
        this.expired = expired;
        return this;
    }

    @Override
    public UserSecurityBuilder<CassandraUserSecurityProxy> locked(Boolean locked) {
        this.locked = locked;
        return this;
    }

    @Override
    public UserSecurityBuilder<CassandraUserSecurityProxy> enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Override
    public UserSecurityBuilder<CassandraUserSecurityProxy> credentialsExpired(Boolean credentialsExpired) {
        this.credentialsExpired = credentialsExpired;
        return this;
    }

    @Override
    public CassandraUserSecurityProxy build() {
        return this.originalUserSecurity
            .orElseGet(() -> userSecurityProxyFactory.create(
                new CassandraUserSecurityImpl().setDiscriminator("1")
            ))
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
            .setEnabled(this.enabled)
            .setExpired(this.expired)
            .setLocked(this.locked)
            .setCredentialsExpired(this.credentialsExpired);
    }
}
