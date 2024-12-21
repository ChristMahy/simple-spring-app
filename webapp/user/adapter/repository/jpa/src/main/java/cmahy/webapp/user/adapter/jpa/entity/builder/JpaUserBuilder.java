package cmahy.webapp.user.adapter.jpa.entity.builder;

import cmahy.webapp.user.adapter.jpa.entity.JpaRole;
import cmahy.webapp.user.adapter.jpa.entity.JpaUser;
import cmahy.webapp.user.kernel.domain.Role;
import cmahy.webapp.user.kernel.domain.builder.UserBuilder;

import java.util.*;

public class JpaUserBuilder implements UserBuilder<JpaUser> {
    
    private Optional<JpaUser> originalUser = Optional.empty();

    private String userName;
    private byte[] password;
    private String fullName;

    private String street;
    private String city;
    private String state;
    private String zip;

    private String phoneNumber;

    private Collection<JpaRole> roles;

    public JpaUserBuilder() {}

    public JpaUserBuilder(JpaUser originalUser) {
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
    public JpaUserBuilder userName(String userName) {
        this.userName = userName;

        return this;
    }

    @Override
    public JpaUserBuilder password(byte[] password) {
        this.password = password;

        return this;
    }

    @Override
    public JpaUserBuilder fullName(String fullName) {
        this.fullName = fullName;

        return this;
    }

    @Override
    public JpaUserBuilder street(String street) {
        this.street = street;

        return this;
    }

    @Override
    public JpaUserBuilder city(String city) {
        this.city = city;

        return this;
    }

    @Override
    public JpaUserBuilder state(String state) {
        this.state = state;

        return this;
    }

    @Override
    public JpaUserBuilder zip(String zip) {
        this.zip = zip;

        return this;
    }

    @Override
    public JpaUserBuilder phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;

        return this;
    }

    @Override
    public <R extends Role> JpaUserBuilder roles(Collection<R> roles) {
        this.roles = (List<JpaRole>) roles;

        return this;
    }

    @Override
    public JpaUser build() {
        return this.originalUser.orElseGet(JpaUser::new)
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
