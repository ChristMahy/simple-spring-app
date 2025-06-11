package cmahy.simple.spring.webapp.user.kernel.domain.builder;

import cmahy.simple.spring.webapp.user.kernel.domain.Right;
import cmahy.simple.spring.webapp.user.kernel.domain.Role;

import java.util.Collection;

public interface RightBuilder<R extends Right> {

    RightBuilder<R> name(String name);

    <ROLE extends Role> RightBuilder<R> roles(Collection<ROLE> roles);

    R build();

}
