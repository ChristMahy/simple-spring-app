package cmahy.simple.spring.webapp.taco.shop.kernel.exception;

import java.util.*;

public abstract class ValidationException  extends TacoException {

    private final Map<String, Set<ValidationMessage>> validationMessages;

    public ValidationException(String message) {
        super(message);

        this.validationMessages = new HashMap<>();
    }

    public Map<String, Set<ValidationMessage>> getValidationMessages() {
        return validationMessages;
    }

    public ValidationException addValidationMessage(String property, ValidationMessage validationMessage) {
        if (!validationMessages.containsKey(property)) {
            validationMessages.put(property, new HashSet<>());
        }

        this.validationMessages.get(property).add(validationMessage);

        return this;
    }
}
