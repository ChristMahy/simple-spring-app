package cmahy.webapp.resource.impl.helper.stream.visitor;

import cmahy.webapp.resource.impl.application.stream.visitor.StreamConsumer;

import java.io.IOException;
import java.io.OutputStream;

public final class StreamVisitorResponseTestImpl {

    private final StreamConsumer consumer;

    public StreamVisitorResponseTestImpl(StreamConsumer consumer) {
        this.consumer = consumer;
    }

    public void execute(OutputStream outputStream) throws IOException {
        consumer.writeTo(outputStream);
    }
}
