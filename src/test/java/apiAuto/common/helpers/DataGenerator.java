package apiAuto.common.helpers;

import java.security.SecureRandom;

public class DataGenerator {

    public static String generateToken() {
        String chars = "ABCDEF0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder token = new StringBuilder(32);
        for (int i = 0; i < 32; i++) {
            token.append(chars.charAt(random.nextInt(chars.length())));
        }
        return token.toString();
    }

    public static String generateToken(String value, int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder token = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            token.append(value.charAt(random.nextInt(value.length())));
        }
        return token.toString();
    }
}
