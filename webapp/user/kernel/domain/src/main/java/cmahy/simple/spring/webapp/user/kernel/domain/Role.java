package cmahy.simple.spring.webapp.user.kernel.domain;

import java.util.Collection;
import java.util.UUID;

public interface Role {

    UUID getId();

    String getName();

    <U extends User> Collection<U> getUsers();

    <R extends Right> Collection<R> getRights();
}
