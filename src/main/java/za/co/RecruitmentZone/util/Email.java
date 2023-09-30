package za.co.RecruitmentZone.util;

import za.co.RecruitmentZone.util.InvalidEmailException;

import java.util.regex.Pattern;

public record Email(String value) {
    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9+_.-]+@(.+)$"; // Basic regex for email validation

    public boolean isValid() {
        // Use regex to validate the email address
        boolean isValid = Pattern.matches(EMAIL_REGEX, value);

        if (!isValid) {
            throw new InvalidEmailException("Invalid email address: " + value);
        }
        return true;
    }
}
