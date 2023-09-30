package za.co.RecruitmentZone.util;

public class InvalidEmailException extends RuntimeException  {
    public InvalidEmailException(String message) {
        super(message);
    }
}

