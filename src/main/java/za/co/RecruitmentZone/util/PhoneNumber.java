package za.co.RecruitmentZone.util;

import java.util.regex.Pattern;

public record PhoneNumber(String value) {
    private static final String PHONE_NUMBER_REGEX =
            "^(?:0[1-8]\\d{8}|0[6-8]\\d{8})$";  // Regex to match SA landline and mobile numbers

    public boolean isValid() {
        if (value == null || !Pattern.matches(PHONE_NUMBER_REGEX, value)) {
            throw new InvalidPhoneNumberException("Invalid phone number format: " + value);
        }
        return true;
    }
}

// Define the custom exception class


