package cmahy.simple.spring.webapp.user.kernel.exception;

import java.util.*;

public class ValidationException extends UserException {

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
