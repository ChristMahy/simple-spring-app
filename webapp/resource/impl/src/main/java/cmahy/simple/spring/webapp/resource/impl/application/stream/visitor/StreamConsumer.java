package cmahy.simple.spring.webapp.resource.impl.application.stream.visitor;

import java.io.IOException;
import java.io.OutputStream;

@FunctionalInterface
public interface StreamConsumer {

    void writeTo(OutputStream outputStream) throws IOException;
}
