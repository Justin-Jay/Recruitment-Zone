package za.co.recruitmentzone.employee.exception;

public class AuthoritiesNotFoundException extends RuntimeException {
    public AuthoritiesNotFoundException(String message) {
        super(message);
    }

    public AuthoritiesNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthoritiesNotFoundException(Throwable cause) {
        super(cause);
    }
}
