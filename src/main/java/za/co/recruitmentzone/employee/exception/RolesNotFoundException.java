package za.co.recruitmentzone.employee.exception;

public class RolesNotFoundException extends RuntimeException {
    public RolesNotFoundException(String message) {
        super(message);
    }

    public RolesNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RolesNotFoundException(Throwable cause) {
        super(cause);
    }
}
