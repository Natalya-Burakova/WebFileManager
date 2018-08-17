package fileManager.app.services;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public class Validation {
    private Validation() { }

    public static void assertNotBlank(String username, String message) {
        if (StringUtils.isBlank(username)) throw new IllegalArgumentException(message);
    }

    public static void assertMinimumLength(String username, int length, String message) {
        if (username.length() < length) throw new IllegalArgumentException(message);
    }

    public static void assertMatches(String email, Pattern regex, String message) {
        if (!regex.matcher(email).matches()) throw new IllegalArgumentException(message);
    }
}
