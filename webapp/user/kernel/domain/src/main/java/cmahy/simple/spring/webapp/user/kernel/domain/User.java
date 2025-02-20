package cmahy.simple.spring.webapp.user.kernel.domain;

import java.util.Collection;
import java.util.UUID;

public interface User {

    UUID getId();

    String getUserName();

    byte[] getPassword();

    String getFullName();

    String getStreet();

    String getCity();

    String getState();

    String getZip();

    String getPhoneNumber();

    <R extends Role> Collection<R> getRoles();
}
