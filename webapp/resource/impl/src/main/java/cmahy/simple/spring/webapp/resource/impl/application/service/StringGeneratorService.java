package cmahy.simple.spring.webapp.resource.impl.application.service;

import jakarta.inject.Named;

import java.util.Random;
import java.util.stream.IntStream;

@Named
public class StringGeneratorService {

    private final Random randomizer;

    private final String letters;
    private final int lettersLength;

    public StringGeneratorService(Random randomizer) {
        this.randomizer = randomizer;

        this.letters = "abcdefghijklmnopqrstuvwxyz ABCDEFGHIJKLMNOPQRSTUVWXYZ_+-=()*$%0123456789";
        this.lettersLength = letters.length();
    }

    public String execute() {
        return this.execute(50);
    }

    public String execute(int length) {
        final var stringBuilder = new StringBuilder(length);

        IntStream.rangeClosed(1, length)
            .forEach(i -> {
                stringBuilder.append(
                    this.letters.charAt(this.randomizer.nextInt(0, this.lettersLength - 1))
                );
            });

        return stringBuilder.toString();
    }
}
