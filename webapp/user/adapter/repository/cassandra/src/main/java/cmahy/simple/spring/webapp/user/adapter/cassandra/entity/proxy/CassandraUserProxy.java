package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy;

import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraRole;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraUserImpl;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.loader.provider.UserLoaderProvider;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.loader.UserLoader;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.CassandraRoleProxyFactory;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.proxy.factory.provider.CassandraUserProxyFactoryProvider;
import cmahy.simple.spring.webapp.user.kernel.domain.Role;
import cmahy.simple.spring.webapp.user.kernel.domain.User;
import cmahy.simple.spring.webapp.user.kernel.domain.id.RoleId;

import java.util.*;
import java.util.stream.Collectors;

public class CassandraUserProxy implements User {

    private final CassandraUserImpl user;
    private Set<CassandraRoleProxy> roles;
    private final UserLoaderProvider userLoaderProvider;
    private final CassandraUserProxyFactoryProvider factoryProvider;

    public CassandraUserProxy(
        CassandraUserImpl user,
        UserLoaderProvider userLoaderProvider,
        CassandraUserProxyFactoryProvider factoryProvider
    ) {
        this.user = user;
        this.userLoaderProvider = userLoaderProvider;
        this.factoryProvider = factoryProvider;
    }

    public CassandraUserImpl unwrap() {
        return user;
    }

    @Override
    public UUID getId() {
        return user.getId();
    }

    @Override
    public String getUserName() {
        return user.getUserName();
    }

    public CassandraUserProxy setUserName(String userName) {
        user.setUserName(userName);
        return this;
    }

    @Override
    public byte[] getPassword() {
        return user.getPassword();
    }

    public CassandraUserProxy setPassword(byte[] password) {
        user.setPassword(password);
        return this;
    }

    @Override
    public String getFullName() {
        return user.getFullName();
    }

    public CassandraUserProxy setFullName(String fullName) {
        user.setFullName(fullName);
        return this;
    }

    @Override
    public String getStreet() {
        return user.getStreet();
    }

    public CassandraUserProxy setStreet(String street) {
        user.setStreet(street);
        return this;
    }

    @Override
    public String getCity() {
        return user.getCity();
    }

    public CassandraUserProxy setCity(String city) {
        user.setCity(city);
        return this;
    }

    @Override
    public String getState() {
        return user.getState();
    }

    public CassandraUserProxy setState(String state) {
        user.setState(state);
        return this;
    }

    @Override
    public String getZip() {
        return user.getZip();
    }

    public CassandraUserProxy setZip(String zip) {
        user.setZip(zip);
        return this;
    }

    @Override
    public String getPhoneNumber() {
        return user.getPhoneNumber();
    }

    public CassandraUserProxy setPhoneNumber(String phoneNumber) {
        user.setPhoneNumber(phoneNumber);
        return this;
    }

    @Override
    public Collection<CassandraRoleProxy> getRoles() {
        if (roles == null) {
            CassandraRoleProxyFactory factory = factoryProvider.resolve(CassandraRole.class);

            roles = ((UserLoader) userLoaderProvider.resolve(CassandraUserImpl.class))
                .loadRoles(user.getCassandraRoleIds())
                .stream()
                .map(factory::create)
                .collect(Collectors.toSet());
        }

        return roles;
    }

    public CassandraUserProxy setRoles(Set<CassandraRoleProxy> roles) {
        this.roles = Optional.ofNullable(roles).orElseGet(HashSet::new);

        // TODO: Multi NPE on this.user ????
        this.user.setCassandraRoleIds(
            this.roles.stream()
                .map(Role::getId)
                .map(RoleId::new)
                .collect(Collectors.toSet())
        );

        return this;
    }

    @Override
    public String toString() {
        return user.toString();
    }
}
