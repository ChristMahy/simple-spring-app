package cmahy.webapp.resource.impl.helper.stream.visitor;

import cmahy.webapp.resource.impl.application.stream.visitor.StreamConsumer;
import cmahy.webapp.resource.impl.application.stream.visitor.StreamVisitor;

import java.util.Optional;

public final class StreamVisitorTestImpl implements StreamVisitor<String, StreamConsumer, StreamVisitorResponseTestImpl> {

    private Optional<String> prepareResult;

    public StreamVisitorTestImpl() {
        this.prepareResult = Optional.empty();
    }

    public Optional<String> getPrepareResult() {
        return prepareResult;
    }

    @Override
    public void prepare(String prepare) {
        prepareResult = Optional.of(prepare);
    }

    @Override
    public StreamVisitorResponseTestImpl build(StreamConsumer streamConsumer) {
        return new StreamVisitorResponseTestImpl(streamConsumer);
    }
}
