package cmahy.springapp.reactive.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.Random;
import java.util.stream.IntStream;

@Service
public class ReadmeGeneratorService {

    private final StringGeneratorService stringGeneratorService;
    private final Random randomizer;

    public ReadmeGeneratorService(StringGeneratorService stringGeneratorService, Random randomizer) {
        this.stringGeneratorService = stringGeneratorService;
        this.randomizer = randomizer;
    }

    public Flux<byte[]> execute(Boolean onFailure) {
        final var failAt = randomizer.nextInt(50, 1020);

        return Flux.fromStream(IntStream.range(0, 1024).boxed())
            .handle((index, sink) -> {
                if (Boolean.TRUE.equals(onFailure) && index >= failAt) {
                    sink.error(new IOException("Ho an exception..."));
                    return;
                }

                sink.next(("Line <" + (index + 1) + ">, " + stringGeneratorService.generateAString(5 * 1024) + "\r\n\r\n\r\n").getBytes());
            });
    }
}
