package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy;

import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraUserSecurityImpl;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.loader.UserSecurityLoader;
import cmahy.simple.spring.webapp.user.kernel.domain.*;
import cmahy.simple.spring.webapp.user.kernel.domain.id.RoleId;

import java.util.*;
import java.util.stream.Collectors;

public class CassandraUserSecurityProxy implements UserSecurity {

    private final CassandraUserSecurityImpl userSecurity;
    private Set<CassandraRoleProxy> roles;
    private final UserSecurityLoader userLoader;

    public CassandraUserSecurityProxy(
        CassandraUserSecurityImpl userSecurity,
        UserSecurityLoader userLoader
    ) {
        this.userSecurity = userSecurity;
        this.userLoader = userLoader;
    }

    public CassandraUserSecurityImpl unwrap() {
        return userSecurity;
    }

    @Override
    public AuthProvider getAuthProvider() {
        return userSecurity.getAuthProvider();
    }

    public CassandraUserSecurityProxy setAuthProvider(AuthProvider authProvider) {
        userSecurity.setAuthProvider(authProvider);
        return this;
    }

    @Override
    public Boolean getExpired() {
        return userSecurity.getExpired();
    }

    public CassandraUserSecurityProxy setExpired(Boolean expired) {
        userSecurity.setExpired(expired);
        return this;
    }

    @Override
    public Boolean getLocked() {
        return userSecurity.getLocked();
    }

    public CassandraUserSecurityProxy setLocked(Boolean locked) {
        userSecurity.setLocked(locked);
        return this;
    }

    @Override
    public Boolean getEnabled() {
        return userSecurity.getEnabled();
    }

    public CassandraUserSecurityProxy setEnabled(Boolean enabled) {
        userSecurity.setEnabled(enabled);
        return this;
    }

    @Override
    public Boolean getCredentialsExpired() {
        return userSecurity.getCredentialsExpired();
    }

    public CassandraUserSecurityProxy setCredentialsExpired(Boolean credentialsExpired) {
        userSecurity.setCredentialsExpired(credentialsExpired);
        return this;
    }

    @Override
    public UUID getId() {
        return userSecurity.getId();
    }

    @Override
    public String getUserName() {
        return userSecurity.getUserName();
    }

    public CassandraUserSecurityProxy setUserName(String userName) {
        userSecurity.setUserName(userName);
        return this;
    }

    @Override
    public byte[] getPassword() {
        return userSecurity.getPassword();
    }

    public CassandraUserSecurityProxy setPassword(byte[] password) {
        userSecurity.setPassword(password);
        return this;
    }

    @Override
    public String getFullName() {
        return userSecurity.getFullName();
    }

    public CassandraUserSecurityProxy setFullName(String fullName) {
        userSecurity.setFullName(fullName);
        return this;
    }

    @Override
    public String getStreet() {
        return userSecurity.getStreet();
    }

    public CassandraUserSecurityProxy setStreet(String street) {
        userSecurity.setStreet(street);
        return this;
    }

    @Override
    public String getCity() {
        return userSecurity.getCity();
    }

    public CassandraUserSecurityProxy setCity(String city) {
        userSecurity.setCity(city);
        return this;
    }

    @Override
    public String getState() {
        return userSecurity.getState();
    }

    public CassandraUserSecurityProxy setState(String state) {
        userSecurity.setState(state);
        return this;
    }

    @Override
    public String getZip() {
        return userSecurity.getZip();
    }

    public CassandraUserSecurityProxy setZip(String zip) {
        userSecurity.setZip(zip);
        return this;
    }

    @Override
    public String getPhoneNumber() {
        return userSecurity.getPhoneNumber();
    }

    public CassandraUserSecurityProxy setPhoneNumber(String phoneNumber) {
        userSecurity.setPhoneNumber(phoneNumber);
        return this;
    }

    @Override
    public Collection<CassandraRoleProxy> getRoles() {
        if (roles == null) {
            roles = userLoader.loadRoles(userSecurity.getCassandraRoleIds());
        }

        return roles;
    }

    public CassandraUserSecurityProxy setRoles(Set<CassandraRoleProxy> roles) {
        this.roles = Optional.ofNullable(roles).orElseGet(HashSet::new);

        // TODO: Multi NPE on this.user ????
        this.userSecurity.setCassandraRoleIds(
            this.roles.stream()
                .map(Role::getId)
                .map(RoleId::new)
                .collect(Collectors.toSet())
        );

        return this;
    }

    @Override
    public String toString() {
        return userSecurity.toString();
    }
}
