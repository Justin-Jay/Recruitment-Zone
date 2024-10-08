package za.co.recruitmentzone.application.dto;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class IDNumberValidation implements ConstraintValidator<RSAIDNumber, String> {

    private String id_Number;

    private static final int ID_NUMBER_LENGTH = 13;

    private static final String ID_NUMBER_REGEX = "\\d{13}";

    @Override
    public boolean isValid(String idNumber, ConstraintValidatorContext context) {
        if (idNumber == null || !Pattern.matches(ID_NUMBER_REGEX, idNumber)) {
            return false;
        }

        if (!isValidDate(idNumber.substring(0, 6)) || !isValidGenderSequence(idNumber.substring(6, 10))
                || !isValidCitizenship(idNumber.charAt(10)) || !isValidCheckDigit(idNumber)) {
            return false;
        }

        return true;
    }

    private boolean isValidDate(String dateStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
            LocalDate.parse(dateStr, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private boolean isValidGenderSequence(String genderSequenceStr) {
        int genderSequence = Integer.parseInt(genderSequenceStr);
        return genderSequence >= 0 && genderSequence <= 9999;
    }

    private boolean isValidCitizenship(char citizenshipChar) {
        return citizenshipChar == '0' || citizenshipChar == '1';
    }

    private boolean isValidCheckDigit(String idNumber) {
        int sum = 0;
        for (int i = 0; i < idNumber.length() - 1; i++) {
            char ch = idNumber.charAt(i);
            int digit = Character.getNumericValue(ch);
            if (i % 2 == 0) {
                sum += digit;
            } else {
                sum += sumDigits(digit * 2);
            }
        }

        int checkDigit = 10 - (sum % 10);
        return checkDigit == Character.getNumericValue(idNumber.charAt(12));
    }

    private int sumDigits(int number) {
        return number / 10 + number % 10;
    }
}


