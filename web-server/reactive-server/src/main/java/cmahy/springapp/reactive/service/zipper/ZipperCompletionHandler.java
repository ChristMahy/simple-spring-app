package cmahy.springapp.reactive.service.zipper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.FluxSink;

import java.io.IOException;
import java.nio.channels.CompletionHandler;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class ZipperCompletionHandler implements CompletionHandler<Integer, ZipperAttachment> {

    private static final Logger LOG = LoggerFactory.getLogger(ZipperCompletionHandler.class);

    private final AtomicReference<State> state;
    private final ZipperAsynchronousChannel channel;
    private final FluxSink<byte[]> emitter;
    private final AtomicInteger position;
    private final Integer failureAt;

    public ZipperCompletionHandler(
        ZipperAsynchronousChannel channel,
        FluxSink<byte[]> emitter,
        Integer failureAt
    ) {
        this.channel = channel;
        this.emitter = emitter;
        this.state = new AtomicReference<>(State.IDLE);
        this.position = new AtomicInteger(0);

        this.failureAt = failureAt;
    }

    public void request(Long n) {
        this.tryRead();
    }

    public void cancel() {
        this.state.set(State.DISPOSED);
        this.closeChannel();
    }

    private void tryRead() {
        if (this.emitter.requestedFromDownstream() > 0L && this.state.compareAndSet(State.IDLE, State.READING)) {
            this.read();
        }
    }

    private void read() {
        ZipperAttachment attachment = new ZipperAttachment();

        this.channel.read(emitter::next, position.get(), attachment, this);
    }

    @Override
    public void completed(Integer result, ZipperAttachment attachment) {
        if (this.state.get().equals(State.DISPOSED)) {
            this.closeChannel();
        } else if (Objects.equals(position.get(), failureAt)) {
            LOG.info("Oh an exception !");

            this.failed(new IOException("Oh, an exception... !"), attachment);
        } else if (position.get() >= 1025) {
            this.channel.finish(emitter::next, this);

            this.closeChannel();
            this.state.set(State.DISPOSED);

            this.emitter.complete();
        } else {
            this.position.incrementAndGet();

            if (this.emitter.requestedFromDownstream() > 0L) {
                this.read();
            } else if (this.state.compareAndSet(State.READING, State.IDLE)) {
                this.tryRead();
            }
        }
    }

    @Override
    public void failed(Throwable exc, ZipperAttachment attachment) {
        this.state.set(State.DISPOSED);
        this.closeChannel();

        this.emitter.error(exc);
    }

    private void closeChannel() {
        if (this.channel != null && this.channel.isOpen()) {
            try {
                this.channel.close();
            } catch (IOException io) {
            }
        }
    }

    private enum State {
        IDLE,
        READING,
        DISPOSED;

        State() {
        }
    }
}
