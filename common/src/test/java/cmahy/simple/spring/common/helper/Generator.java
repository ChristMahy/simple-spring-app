package cmahy.simple.spring.common.helper;

import java.util.Random;
import java.util.UUID;

public final class Generator {

    private static final String LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String SPECIAL_CHARS = " _+-=()*$%$@#:/;.,?";
    private static final String NUMBERS = "0123456789";

    private static final String LETTERS_NUMBERS_SPECIAL_CHARS = LETTERS + SPECIAL_CHARS + NUMBERS;
    private static final int LETTERS_NUMBERS_SPECIAL_CHARS_LENGTH = LETTERS_NUMBERS_SPECIAL_CHARS.length();

    private static final String LETTERS_NUMBERS = LETTERS + NUMBERS;
    private static final int LETTERS_NUMBERS_LENGTH = LETTERS_NUMBERS.length();

    private static final Random RANDOM = new Random();

    private Generator() {}

    public static String generateAString() {
        return generateAString(20);
    }

    public static String generateAString(int length) {
        final var stringBuilder = new StringBuilder(length);

        while (length-- > 0) {
            stringBuilder.append(
                LETTERS_NUMBERS_SPECIAL_CHARS.charAt(randomInt(0, LETTERS_NUMBERS_SPECIAL_CHARS_LENGTH - 1))
            );
        }

        return stringBuilder.toString();
    }

    public static String generateAStringWithoutSpecialChars() {
        return generateAStringWithoutSpecialChars(20);
    }

    public static String generateAStringWithoutSpecialChars(int length) {
        final var stringBuilder = new StringBuilder(length);

        while (length-- > 0) {
            stringBuilder.append(
                LETTERS_NUMBERS.charAt(randomInt(0, LETTERS_NUMBERS_LENGTH - 1))
            );
        }

        return stringBuilder.toString();
    }

    public static UUID randomUUID() {
        return UUID.randomUUID();
    }

    public static int randomInt() {
        return randomInt(null, null);
    }

    public static int randomIntEqualOrAboveZero() {
        return randomInt(0, Integer.MAX_VALUE);
    }

    public static int randomInt(Integer min, Integer max) {
        return new Random().nextInt(min == null ? Integer.MIN_VALUE : min, max == null || max == Integer.MAX_VALUE ? Integer.MAX_VALUE : (max + 1));
    }

    public static Long randomLongEqualOrAboveZero() {
        return randomLong(0L, Long.MAX_VALUE);
    }

    public static Long randomLong() {
        return randomLong(null, null);
    }

    public static Long randomLong(Long min, Long max) {
        return RANDOM.nextLong(min == null ? Long.MIN_VALUE : min, max == null || max == Long.MAX_VALUE ? Long.MAX_VALUE : (max + 1));
    }

    public static Boolean randomBoolean() {
        return RANDOM.nextBoolean();
    }

    public static <T extends Enum<T>> T randomEnum(Class<T> enumClass) {
        final var values = enumClass.getEnumConstants();

        return values[randomInt(0, values.length - 1)];
    }

    public static byte[] randomBytes(int length) {
        final byte[] bytes = new byte[length];

        RANDOM.nextBytes(bytes);

        return bytes;
    }
}
