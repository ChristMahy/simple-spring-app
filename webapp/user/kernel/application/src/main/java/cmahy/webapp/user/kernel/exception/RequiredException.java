package cmahy.webapp.user.kernel.exception;

public class RequiredException extends UserRuntimeException {

    public RequiredException(Class<?> classType) {
        super(classType.getName() + " has not to be null");
    }
}
