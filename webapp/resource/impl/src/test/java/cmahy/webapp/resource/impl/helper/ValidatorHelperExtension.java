package cmahy.webapp.resource.impl.helper;

import org.junit.jupiter.api.extension.*;

import java.util.Optional;

public final class ValidatorHelperExtension implements AfterEachCallback, ParameterResolver {

    private Optional<ValidatorHelper> instance = Optional.empty();

    @Override
    public void afterEach(ExtensionContext extensionContext) throws Exception {
        if (instance.isPresent()) {
            instance.get().close();
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(ValidatorHelper.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        if (instance.isEmpty()) {
            instance = Optional.of(new ValidatorHelper());
        }

        return instance.get();
    }
}
