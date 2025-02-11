package cmahy.webapp.user.adapter.cassandra.entity.builder;

import cmahy.webapp.user.adapter.cassandra.entity.domain.CassandraUserImpl;
import cmahy.webapp.user.adapter.cassandra.entity.proxy.CassandraRoleProxy;
import cmahy.webapp.user.adapter.cassandra.entity.proxy.CassandraUserProxy;
import cmahy.webapp.user.adapter.cassandra.entity.proxy.factory.CassandraUserProxyFactory;
import cmahy.webapp.user.kernel.domain.Role;
import cmahy.webapp.user.kernel.domain.builder.UserBuilder;

import java.util.*;

public class CassandraUserBuilder implements UserBuilder<CassandraUserProxy> {

    private final CassandraUserProxyFactory userProxyFactory;
    private Optional<CassandraUserProxy> originalUser = Optional.empty();

    private String userName;
    private byte[] password;
    private String fullName;

    private String street;
    private String city;
    private String state;
    private String zip;

    private String phoneNumber;

    private Set<CassandraRoleProxy> roles;

    public CassandraUserBuilder(CassandraUserProxyFactory userProxyFactory) {
        this.userProxyFactory = userProxyFactory;
    }

    public CassandraUserBuilder(CassandraUserProxyFactory userProxyFactory, CassandraUserProxy originalUser) {
        this(userProxyFactory);

        this.originalUser = Optional.ofNullable(originalUser);

        this.originalUser.ifPresent(user -> {
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
    public CassandraUserBuilder userName(String userName) {
        this.userName = userName;
        return this;
    }

    @Override
    public CassandraUserBuilder password(byte[] password) {
        this.password = password;
        return this;
    }

    @Override
    public CassandraUserBuilder fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    @Override
    public CassandraUserBuilder street(String street) {
        this.street = street;
        return this;
    }

    @Override
    public CassandraUserBuilder city(String city) {
        this.city = city;
        return this;
    }

    @Override
    public CassandraUserBuilder state(String state) {
        this.state = state;
        return this;
    }

    @Override
    public CassandraUserBuilder zip(String zip) {
        this.zip = zip;
        return this;
    }

    @Override
    public CassandraUserBuilder phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    @Override
    public <R extends Role> CassandraUserBuilder roles(Collection<R> roles) {
        this.roles = (Set<CassandraRoleProxy>) roles;
        return this;
    }

    @Override
    public CassandraUserProxy build() {
        return this.originalUser
            .orElseGet(() -> userProxyFactory.create(
                new CassandraUserImpl().setDiscriminator("0")
            ))
            .setUserName(this.userName)
            .setPassword(this.password)
            .setFullName(this.fullName)
            .setStreet(this.street)
            .setCity(this.city)
            .setState(this.state)
            .setZip(this.zip)
            .setPhoneNumber(this.phoneNumber)
            .setRoles(this.roles);
    }
}
