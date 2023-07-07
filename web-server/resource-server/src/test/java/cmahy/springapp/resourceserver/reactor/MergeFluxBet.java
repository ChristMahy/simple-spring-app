package cmahy.springapp.resourceserver.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

import java.time.Duration;

public class MergeFluxBet {

    @Test
    void mergeFluxes() {
        Flux<String> characterFlux = Flux.just(
            "Garfield", "Kojak", "Barbossa"
        )
            .delayElements(Duration.ofMillis(500));

        Flux<String> foodFlux = Flux.just(
            "Lasagna", "Lollipops", "Apples"
        )
            .delaySubscription(Duration.ofMillis(250))
            .delayElements(Duration.ofMillis(500));

        Flux<String> mergedFlux = characterFlux.mergeWith(foodFlux);

        StepVerifier.create(mergedFlux)
            .expectNext("Garfield", "Lasagna", "Kojak", "Lollipops", "Barbossa", "Apples")
            .verifyComplete();
    }

    @Test
    void zipFluxes() {
        Flux<String> characterFlux = Flux.just(
                "Garfield", "Kojak", "Barbossa"
            );

        Flux<String> foodFlux = Flux.just(
                "Lasagna", "Lollipops", "Apples"
            );

        Flux<Tuple2<String, String>> zippedFlux = Flux.zip(characterFlux, foodFlux);

        StepVerifier.create(zippedFlux)
            .expectNextMatches(p ->
                "Garfield".equals(p.getT1()) &&
                    "Lasagna".equals(p.getT2())
            )
            .expectNextMatches(p ->
                "Kojak".equals(p.getT1()) &&
                    "Lollipops".equals(p.getT2())
            )
            .expectNextMatches(p ->
                "Barbossa".equals(p.getT1()) &&
                    "Apples".equals(p.getT2())
            )
            .verifyComplete();
    }

    @Test
    void zipFluxesToObject() {
        Flux<String> characterFlux = Flux.just(
                "Garfield", "Kojak", "Barbossa"
            );

        Flux<String> foodFlux = Flux.just(
                "Lasagna", "Lollipops", "Apples"
            );

        Flux<String> zippedFlux = Flux.zip(characterFlux, foodFlux, (c, f) -> c + " eats " + f);

        StepVerifier.create(zippedFlux)
            .expectNext("Garfield eats Lasagna", "Kojak eats Lollipops", "Barbossa eats Apples")
            .verifyComplete();
    }

    @Test
    void firstWithSignalFlux() {

        Flux<String> slowFlux = Flux.just("tortoise", "snail", "sloth")
            .delaySubscription(Duration.ofMillis(500));

        Flux<String> fastFlux = Flux.just("hare", "cheetah", "squirrel");

        Flux<String> firstFlux = Flux.firstWithSignal(slowFlux, fastFlux);

        StepVerifier.create(firstFlux)
            .expectNext("hare", "cheetah", "squirrel")
            .verifyComplete();
    }
}
