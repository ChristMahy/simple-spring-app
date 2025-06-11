package cmahy.simple.spring.webapp.user.kernel.domain;

import java.util.Collection;
import java.util.UUID;

public interface Right {

    UUID getId();

    String getName();

    <R extends Role> Collection<R> getRoles();
}
