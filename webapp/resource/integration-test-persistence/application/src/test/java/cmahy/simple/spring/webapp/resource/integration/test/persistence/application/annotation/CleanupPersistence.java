package cmahy.simple.spring.webapp.resource.integration.test.persistence.application.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CleanupPersistence {

    boolean required() default true;

    String[] ignoreTables() default {};

}
