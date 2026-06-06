package cmahy.simple.spring.webapp.resource.impl.adapter.taco.shop.properties.nested;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.Strings;

public class SampleConstraintValidator implements ConstraintValidator<SampleConstraint, String> {

    private final Strings ciStrings;

    public SampleConstraintValidator(Strings ciStrings) {
        this.ciStrings = ciStrings;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        try {
            if (ciStrings.equals(value, "exact-match")) {
                return true;
            }
        } catch (Exception any) {
            return false;
        }

        return false;
    }
}
