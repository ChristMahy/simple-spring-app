package cmahy.common.validation;

import cmahy.common.validation.exception.PublicAccessorNotFoundException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import java.lang.reflect.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static org.slf4j.LoggerFactory.getLogger;

public class StringEqualityValidator implements ConstraintValidator<StringEquality, Object> {

    private static final Logger LOG = getLogger(StringEqualityValidator.class);

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        try {
            if (Objects.isNull(value)) {
                return true;
            }

            Method[] allDeclaredMethods = value.getClass().getDeclaredMethods();

            List<Field> fieldsWithAnnotation = Arrays.stream(value.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(StringEqualityField.class))
                .toList();

            if (fieldsWithAnnotation.isEmpty()) {
                return true;
            }

            if (fieldsWithAnnotation.stream().anyMatch(field -> !String.class.equals(field.getType()))) {
                return false;
            }

            Method first = exctractMethod(allDeclaredMethods, fieldsWithAnnotation.stream().findFirst().get());
            boolean ignoreCase = value.getClass().getAnnotation(StringEquality.class).ignoreCase();

            for (Field field : fieldsWithAnnotation) {
                Method method = exctractMethod(allDeclaredMethods, field);

                if (ignoreCase) {
                    if (!StringUtils.equalsIgnoreCase(method.invoke(value).toString(), first.invoke(value).toString())) {
                        return false;
                    }
                } else if (!StringUtils.equals(method.invoke(value).toString(), first.invoke(value).toString())) {
                    return false;
                }
            }

            return true;
        } catch (
            PublicAccessorNotFoundException |
            InvocationTargetException |
            IllegalAccessException any
        ) {
            LOG.warn(any.getMessage(), any);

            return false;
        }
    }

    private Method exctractMethod(Method[] allDeclaredMethods, Field field) throws PublicAccessorNotFoundException {
        char firstLetter = field.getName().charAt(0);
        Predicate<String> methodNameMatcher = Pattern.compile(
            "^(get)?" + "[" + firstLetter + "|" + Character.toUpperCase(firstLetter) + "]" + Pattern.quote(field.getName().substring(1)) + "$"
        ).asMatchPredicate();

        List<Method> filteredMethods = Arrays.stream(allDeclaredMethods).filter(method -> methodNameMatcher.test(method.getName())).toList();

        if (filteredMethods.isEmpty()) {
            throw new PublicAccessorNotFoundException("Public accessor not found for <" + field.getName() + ">");
        }

        if (filteredMethods.size() == 1) {
            return filteredMethods.stream().findFirst().get();
        }

        filteredMethods = filteredMethods.stream()
            .filter(method ->
                method.getParameterCount() == 0 &&
                    Objects.equals(method.getReturnType(), field.getType()) &&
                    methodNameMatcher.test(method.getName())
            )
            .toList();

        if (filteredMethods.size() != 1) {
            throw new PublicAccessorNotFoundException("Too much public accessors found for <" + field.getName() + ">, <" + filteredMethods.size() + ">");
        }

        return filteredMethods.stream().findFirst().get();
    }
}
