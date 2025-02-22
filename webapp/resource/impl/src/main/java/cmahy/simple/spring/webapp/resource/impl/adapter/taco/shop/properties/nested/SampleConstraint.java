package cmahy.simple.spring.webapp.resource.impl.adapter.taco.shop.properties.nested;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD, PARAMETER })
@Retention(RUNTIME)
@Constraint(validatedBy = SampleConstraintValidator.class)
@Documented
public @interface SampleConstraint {

    String message() default "Doit être égale à 'exact-match'";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
