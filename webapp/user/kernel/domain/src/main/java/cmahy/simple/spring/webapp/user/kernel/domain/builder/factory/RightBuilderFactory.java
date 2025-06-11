package cmahy.simple.spring.webapp.user.kernel.domain.builder.factory;

import cmahy.simple.spring.webapp.user.kernel.domain.Right;
import cmahy.simple.spring.webapp.user.kernel.domain.builder.RightBuilder;

public interface RightBuilderFactory<R extends Right> {

    RightBuilder<R> create();

    RightBuilder<R> create(R right);

}
