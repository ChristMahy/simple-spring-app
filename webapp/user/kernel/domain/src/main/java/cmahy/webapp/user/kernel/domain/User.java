package cmahy.webapp.user.kernel.domain;

import java.util.Collection;

public interface User {

    Long getId();

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
