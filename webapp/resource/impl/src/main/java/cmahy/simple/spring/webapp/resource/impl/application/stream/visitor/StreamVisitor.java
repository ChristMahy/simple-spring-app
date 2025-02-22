package cmahy.simple.spring.webapp.resource.impl.application.stream.visitor;

public interface StreamVisitor<PREPARE, BODY, RESPONSE> {

    default void prepare(PREPARE prepare) {}

    RESPONSE build(BODY body);
}
