package cmahy.springapp.reactive.controller.handler;

import cmahy.springapp.reactive.service.StringGeneratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

@Component
public class StringGeneratorHandler {

    private static final Logger LOG = LoggerFactory.getLogger(StringGeneratorHandler.class);

    private final StringGeneratorService stringGeneratorService;
    private final Random randomizer;

    public StringGeneratorHandler(
        StringGeneratorService stringGeneratorService,
        Random randomizer
    ) {
        this.stringGeneratorService = stringGeneratorService;
        this.randomizer = randomizer;
    }

    public Mono<ServerResponse> generator(ServerRequest webRequest) {
        return randomizer.nextBoolean() ?
            this.generatorWithCompletableFuture(webRequest) :
            this.generatorWithPublishOnAsync(webRequest);
    }

    public Mono<ServerResponse> generatorWithCompletableFuture(ServerRequest webRequest) {
        LOG.info("Generate string with CompletableFuture");

        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(
                Flux.create(emitter -> {
                    CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                        IntStream.range(1, 1025).forEach(currentCpt -> {
                            LOG.info("Current position: <{}>", currentCpt);

                            emitter.next((stringGeneratorService.generateAString(5 * 1024 * 1024) + " <=> " + currentCpt).getBytes());
                        });
                    });

                    future.whenCompleteAsync((bytes, throwable) -> {
                        if (throwable != null) {
                            emitter.error(throwable);
                        }

                        emitter.complete();
                    });
                }),
                byte[].class
            );
    }

    public Mono<ServerResponse> generatorWithPublishOnAsync(ServerRequest webRequest) {
        LOG.info("Generate string with publishOn async");

        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(
                Flux.range(0, 1024)
                    .publishOn(Schedulers.single())
                    .map(currentCpt -> {
                        LOG.info("Current position: <{}>", currentCpt);

                        return (stringGeneratorService.generateAString(5 * 1024 * 1024) + " <=> " + currentCpt).getBytes();
                    }),
                byte[].class
            );
    }
}
