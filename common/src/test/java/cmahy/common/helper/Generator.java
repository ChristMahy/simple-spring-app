package cmahy.common.helper;

import java.util.Random;

public final class Generator {

    private static final String LETTERS = "abcdefghijklmnopqrstuvwxyz ABCDEFGHIJKLMNOPQRSTUVWXYZ_+-=()*$%0123456789";
    private static final int LETTERS_LENGTH = LETTERS.length();
    private static final Random RANDOM = new Random();

    private Generator() {}

    public static String generateAString() {
        return generateAString(20);
    }

    public static String generateAString(int length) {
        final var stringBuilder = new StringBuilder(length);

        while (length-- > 0) {
            stringBuilder.append(
                LETTERS.charAt(randomInt(0, LETTERS_LENGTH - 1))
            );
        }

        return stringBuilder.toString();
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
