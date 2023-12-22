package cmahy.webapp.resource.impl.application.stream.visitor;

public interface StreamVisitor<P, B, R> {

    default void prepare(P prepare) {}

    R build(B body);
}
