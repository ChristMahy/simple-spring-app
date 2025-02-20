package cmahy.simple.spring.webapp.taco.shop.kernel.helper;

import jakarta.validation.*;
import jakarta.validation.executable.ExecutableValidator;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public final class ValidatorHelper implements AutoCloseable {

    private final ValidatorFactory validatorFactory;

    public ValidatorHelper() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
    }

    @Override
    public void close() throws Exception {
        validatorFactory.close();
    }

    public <T> Set<ConstraintViolation<T>> validateMethod(
        T instance, String methodName, Object[] arguments
    ) {
        return validateMethod(instance, methodName, Collections.emptyList(), arguments);
    }

    public <T> Set<ConstraintViolation<T>> validateMethod(
        T instance, String methodName, List<Class<?>> methodParameterTypes, Object[] arguments
    ) {
        ExecutableValidator executableValidator = validatorFactory.getValidator().forExecutables();

        return executableValidator.validateParameters(
            instance,
            extractMethod(instance, methodName, methodParameterTypes),
            arguments
        );
    }

    public <T, A> Set<ConstraintViolation<T>> validateReturnValue(
        T instance, String methodName, A actual
    ) {
        return validateReturnValue(instance, methodName, Collections.emptyList(), actual);
    }

    public <T, A> Set<ConstraintViolation<T>> validateReturnValue(
        T instance, String methodName, List<Class<?>> parameterTypes, A actual
    ) {
        ExecutableValidator executableValidator = validatorFactory.getValidator().forExecutables();

        return executableValidator.validateReturnValue(
            instance,
            extractMethod(instance, methodName, parameterTypes),
            actual
        );
    }

    public <T> Method extractMethod(T instance, String methodName, List<Class<?>> parameterTypes) {
        return Arrays.stream(instance.getClass().getMethods())
            .filter(method -> StringUtils.equals(method.getName(), methodName) && (parameterTypes.isEmpty() || Arrays.stream(method.getParameterTypes()).allMatch(parameterTypes::contains)))
            .collect(Collectors.collectingAndThen(
                Collectors.toList(),
                list -> list.size() == 1 ? list.stream().findFirst() : Optional.<Method>empty()
            ))
            .orElseThrow();
    }
}
