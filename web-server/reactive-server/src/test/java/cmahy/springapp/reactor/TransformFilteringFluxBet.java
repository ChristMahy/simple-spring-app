package cmahy.springapp.reactor;

import lombok.Data;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public class TransformFilteringFluxBet {

    @Test
    void skipAFew() {

        Flux<String> countFlux = Flux.just(
            "one", "two", "skip a few", "ninety nine", "one hundred"
        ).skip(3);

        StepVerifier.create(countFlux)
            .expectNext("ninety nine", "one hundred")
            .verifyComplete();
    }

    @Test
    void skipAFewSeconds() {

        Flux<String> countFlux = Flux.just(
            "one", "two", "skip a few", "ninety nine", "one hundred"
        )
            .delayElements(Duration.ofSeconds(1))
            .skip(Duration.ofSeconds(4));

        StepVerifier.create(countFlux)
            .expectNext("ninety nine", "one hundred")
            .verifyComplete();
    }

    @Test
    void take() {

        Flux<String> nationalParkFlux = Flux.just(
            "Yellowstone", "Yosemite", "Grand Canyon", "Zion", "Acadia"
        ).take(3);

        StepVerifier.create(nationalParkFlux)
            .expectNext("Yellowstone", "Yosemite", "Grand Canyon")
            .verifyComplete();
    }

    @Test
    void takeForAWhile() {

        Flux<String> nationalParkFlux = Flux.just(
            "Yellowstone", "Yosemite", "Grand Canyon", "Zion", "Acadia"
        )
            .delayElements(Duration.ofSeconds(1))
            .take(Duration.ofMillis(3500));

        StepVerifier.create(nationalParkFlux)
            .expectNext("Yellowstone", "Yosemite", "Grand Canyon")
            .verifyComplete();
    }

    @Test
    void filter() {

        Flux<String> nationalParkFlux = Flux.just(
            "Yellowstone", "Yosemite", "Grand Canyon", "Zion", "Grand Teton"
        )
            .filter(np -> !np.contains(" "));

        StepVerifier.create(nationalParkFlux)
            .expectNext("Yellowstone", "Yosemite", "Zion")
            .verifyComplete();
    }

    @Test
    void distinct() {

        Flux<String> animalFlux = Flux.just(
            "dog", "cat", "bird", "dog", "bird", "anteater"
        )
            .distinct();

        StepVerifier.create(animalFlux)
            .expectNext("dog", "cat", "bird", "anteater")
            .verifyComplete();
    }

    @Test
    void map() {

        Flux<Player> playerFlux = Flux.just(
            "Michael Jordan", "Scottie Pippen", "Steve Kerr"
        )
            .map(p -> {
                String[] split = p.split("\\s");

                return new Player(split[0], split[1]);
            });

        StepVerifier.create(playerFlux)
            .expectNext(new Player("Michael", "Jordan"), new Player("Scottie", "Pippen"), new Player("Steve", "Kerr"))
            .verifyComplete();
    }

    @Test
    void flatMap_asyncExample() {

        Flux<Player> playerFlux = Flux.just(
            "Michael Jordan", "Scottie Pippen", "Steve Kerr"
        )
            .flatMap(n -> Mono.just(n)
                .map(p -> {
                    String[] split = p.split("\\s");

                    return new Player(split[0], split[1]);
                })
                .subscribeOn(Schedulers.parallel())
            );

        List<Player> players = List.of(
            new Player("Michael", "Jordan"),
            new Player("Scottie", "Pippen"),
            new Player("Steve", "Kerr")
        );

        StepVerifier.create(playerFlux)
            .expectNextMatches(players::contains)
            .expectNextMatches(players::contains)
            .expectNextMatches(players::contains)
            .verifyComplete();
    }

    public record Player(
        String firstName,
        String lastName
    ) { }

    @Test
    void buffer() {

        Flux<String> fruitFlux = Flux.just("apple", "orange", "banana", "kiwi", "strawberry");

        Flux<List<String>> bufferedFlux = fruitFlux.buffer(3);

        StepVerifier.create(bufferedFlux)
            .expectNext(List.of("apple", "orange", "banana"), List.of("kiwi", "strawberry"))
            .verifyComplete();
    }

    @Test
    void bufferAndFlatMap() {

        Flux.just("apple", "orange", "banana", "kiwi", "strawberry")
            .buffer(3)
            .flatMap(bufferedFruits -> Flux.fromIterable(bufferedFruits)
                .map(String::toUpperCase)
                .subscribeOn(Schedulers.parallel())
                .log()
            )
            .subscribe();
    }

    @Test
    void collectList() {

        Flux<String> fruitFlux = Flux.just("apple", "orange", "banana", "kiwi", "strawberry");

        Mono<List<String>> fruitListMono = fruitFlux.collectList();

        StepVerifier.create(fruitListMono)
            .expectNext(List.of("apple", "orange", "banana", "kiwi", "strawberry"))
            .verifyComplete();
    }

    @Test
    void collectMap() {

        Flux<String> fruitFlux = Flux.just("ananas", "apple", "orange", "banana", "kiwi", "strawberry");

        Mono<Map<Character, String>> fruitMapMono = fruitFlux.collectMap(f -> f.charAt(0));

        StepVerifier.create(fruitMapMono)
            .expectNext(Map.of(
                'a', "apple",
                'o', "orange",
                'b', "banana",
                'k', "kiwi",
                's', "strawberry"
            ))
            .verifyComplete();
    }

    @Test
    void all() {

        Flux<String> animalFlux = Flux.just(
            "aardvark", "elephant", "koala", "eagle", "kangaroo"
        );

        Mono<Boolean> hasAMono = animalFlux.all(a -> a.contains("a"));

        StepVerifier.create(hasAMono)
            .expectNext(true)
            .verifyComplete();

        Mono<Boolean> hasKMono = animalFlux.all(a -> a.contains("k"));

        StepVerifier.create(hasKMono)
            .expectNext(false)
            .verifyComplete();
    }

    @Test
    void any() {

        Flux<String> animalFlux = Flux.just(
            "aardvark", "elephant", "koala", "eagle", "kangaroo"
        );

        Mono<Boolean> hasTMono = animalFlux.any(a -> a.contains("t"));

        StepVerifier.create(hasTMono)
            .expectNext(true)
            .verifyComplete();

        Mono<Boolean> hasZMono = animalFlux.any(a -> a.contains("z"));

        StepVerifier.create(hasZMono)
            .expectNext(false)
            .verifyComplete();
    }
}
