package cmahy.simple.spring.webapp.resource.integration.test.persistence.api.repository;

import cmahy.simple.spring.webapp.user.kernel.domain.Right;

public interface RightTestRepository<R extends Right> {

    R save(R right);

}
