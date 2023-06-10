package cmahy.springapp.resourceserver.config.properties.nested;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = SampleConstraintValidator.class)
@Documented
public @interface SampleConstraint {
    String message() default "Doit être égale à 'exact-match'";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
