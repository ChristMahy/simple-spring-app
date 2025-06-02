package cmahy.simple.spring.webapp.reactive.resource.impl.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.List;
import java.util.stream.Stream;

public class CreateAFluxBetTest {

    @Test
    void createAFlux_just() {
        Flux<String> fruitFlux = Flux.just(
            "Apple", "Orange", "Grape", "Banana", "Strawberry"
        );

        fruitFlux.subscribe(fruit -> {
            System.out.println("Here's some fruit: " + fruit);
        });

        StepVerifier.create(fruitFlux)
            .expectNext("Apple", "Orange", "Grape", "Banana", "Strawberry")
            .verifyComplete();
    }

    @Test
    void createAFlux_fromArray() {
        String[] fruits = new String[] {"Apple", "Orange", "Grape", "Banana", "Strawberry"};

        Flux<String> fruitFlux = Flux.fromArray(fruits);

        fruitFlux.subscribe(fruit -> {
            System.out.println("Here's some fruit: " + fruit);
        });

        StepVerifier.create(fruitFlux)
            .expectNext("Apple", "Orange", "Grape", "Banana", "Strawberry")
            .verifyComplete();
    }

    @Test
    void createAFlux_fromIterable() {
        List<String> fruits = List.of("Apple", "Orange", "Grape", "Banana", "Strawberry");

        Flux<String> fruitFlux = Flux.fromIterable(fruits);

        fruitFlux.subscribe(fruit -> {
            System.out.println("Here's some fruit: " + fruit);
        });

        StepVerifier.create(fruitFlux)
            .expectNext("Apple", "Orange", "Grape", "Banana", "Strawberry")
            .verifyComplete();
    }

    @Test
    void createAFlux_fromStream() {
        Stream<String> fruits = Stream.of("Apple", "Orange", "Grape", "Banana", "Strawberry");

        Flux<String> fruitFlux = Flux.fromStream(fruits);

        StepVerifier.create(fruitFlux)
            .expectNext("Apple", "Orange", "Grape", "Banana", "Strawberry")
            .verifyComplete();
    }

    @Test
    void createAFlux_range() {
        Flux<Integer> intervalFlux = Flux.range(1, 5);

        StepVerifier.create(intervalFlux)
            .expectNext(1, 2, 3, 4, 5)
            .verifyComplete();
    }

    @Test
    void createAFlux_interval() {
        Flux<Long> intervalFlux = Flux.interval(Duration.ofSeconds(1))
            .take(5);

        StepVerifier.create(intervalFlux)
            .expectNext(0L, 1L, 2L, 3L, 4L)
            .verifyComplete();
    }
}
