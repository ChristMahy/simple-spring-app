package cmahy.simple.spring.common.validation;

import java.lang.annotation.*;

@Documented
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface StringEqualityField {
}
