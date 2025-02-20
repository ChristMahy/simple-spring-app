package cmahy.simple.spring.webapp.user.kernel.domain.builder;

import cmahy.simple.spring.webapp.user.kernel.domain.*;

import java.util.*;

public class UserBuilderStub implements UserBuilder<UserStub> {

    private Optional<UserStub> originalUser = Optional.empty();

    private String userName;
    private byte[] password;
    private String fullName;

    private String street;
    private String city;
    private String state;
    private String zip;

    private String phoneNumber;

    private Collection<RoleStub> roles;

    public UserBuilderStub() {}

    public UserBuilderStub(UserStub originalUser) {
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
    public UserBuilderStub userName(String userName) {
        this.userName = userName;

        return this;
    }

    @Override
    public UserBuilderStub password(byte[] password) {
        this.password = password;

        return this;
    }

    @Override
    public UserBuilderStub fullName(String fullName) {
        this.fullName = fullName;

        return this;
    }

    @Override
    public UserBuilderStub street(String street) {
        this.street = street;

        return this;
    }

    @Override
    public UserBuilderStub city(String city) {
        this.city = city;

        return this;
    }

    @Override
    public UserBuilderStub state(String state) {
        this.state = state;

        return this;
    }

    @Override
    public UserBuilderStub zip(String zip) {
        this.zip = zip;

        return this;
    }

    @Override
    public UserBuilderStub phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;

        return this;
    }

    @Override
    public <R extends Role> UserBuilderStub roles(Collection<R> roles) {
        this.roles = (List<RoleStub>) roles;

        return this;
    }

    @Override
    public UserStub build() {
        return this.originalUser.orElseGet(UserStub::new)
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
