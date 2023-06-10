package cmahy.springapp.resourceserver.config.properties.nested;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

public class SampleConstraintValidator implements ConstraintValidator<SampleConstraint, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            if (StringUtils.equalsIgnoreCase(value, "exact-match")) {
                return true;
            }
        } catch (Exception any) {
            return false;
        }

        return false;
    }
}
