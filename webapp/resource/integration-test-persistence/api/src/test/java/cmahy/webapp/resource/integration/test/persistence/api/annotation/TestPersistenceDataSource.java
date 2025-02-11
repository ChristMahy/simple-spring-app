package cmahy.webapp.resource.integration.test.persistence.api.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TestPersistenceDataSource {
}
