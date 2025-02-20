package cmahy.simple.spring.webapp.user.kernel.domain.builder;

import cmahy.simple.spring.webapp.user.kernel.domain.Role;
import cmahy.simple.spring.webapp.user.kernel.domain.User;

import java.util.Collection;

public interface UserBuilder<U extends User> {

    UserBuilder<U> userName(String userName);

    UserBuilder<U> password(byte[] password);

    UserBuilder<U> fullName(String fullName);

    UserBuilder<U> street(String street);

    UserBuilder<U> city(String city);

    UserBuilder<U> state(String state);

    UserBuilder<U> zip(String zip);

    UserBuilder<U> phoneNumber(String phoneNumber);

    <R extends Role> UserBuilder<U> roles(Collection<R> roles);

    U build();
}
