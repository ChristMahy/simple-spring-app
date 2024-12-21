package cmahy.webapp.user.kernel.domain;

import java.util.Collection;

public interface Role {

    Long getId();

    String getName();

    <U extends User> Collection<U> getUsers();
}
