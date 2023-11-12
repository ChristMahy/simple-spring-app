package cmahy.springapp.reactive.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class StringGeneratorService {

    private final String letters;
    private final int lettersLength;
    private final Random randomizer;

    public StringGeneratorService(Random randomizer) {
        this.letters = "abcdefghijklmnopqrstuvwxyz ABCDEFGHIJKLMNOPQRSTUVWXYZ_+-=()*$%0123456789";
        this.lettersLength = letters.length();
        this.randomizer = randomizer;
    }

    public String generateAString(int length) {
        final var stringBuilder = new StringBuilder(length);

        while (length-- > 0) {
            stringBuilder.append(
                letters.charAt(randomizer.nextInt(0, lettersLength - 1))
            );
        }

        return stringBuilder.toString();
    }
}
