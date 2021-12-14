package br.ufrn.imd.promocon.utils;

import java.util.Random;

public class RandomStringGenerator {
    public static String generate(int targetStringLength) {
        Random random = new Random();

        String string = random.ints(48, 123)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return string;
    }
}
