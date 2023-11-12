package cmahy.springapp.reactive.service;

import cmahy.springapp.reactive.service.zipper.ZipperAsynchronousChannel;
import cmahy.springapp.reactive.service.zipper.ZipperCompletionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.Random;

@Service
public class ZipGeneratorService {

    private static final Logger LOG = LoggerFactory.getLogger(ZipGeneratorService.class);

    private final StringGeneratorService stringGeneratorService;
    private final Random randomizer;

    public ZipGeneratorService(
        StringGeneratorService stringGeneratorService,
        Random randomizer
    ) {
        this.stringGeneratorService = stringGeneratorService;
        this.randomizer = randomizer;
    }

    public Flux<byte[]> execute(Boolean onFailure) {
        return Flux.using(
            () -> new ZipperAsynchronousChannel(stringGeneratorService),
            channel -> Flux.create(emitter -> {
                LOG.info("Flux create");

                var handler = new ZipperCompletionHandler(
                    channel,
                    emitter,
                    Boolean.TRUE.equals(onFailure) ? randomizer.nextInt(100, 1020) : null
                );

                emitter.onCancel(handler::cancel);
                emitter.onRequest(handler::request);
            }, FluxSink.OverflowStrategy.LATEST),
            channel -> {},
            false
        );
    }
}
