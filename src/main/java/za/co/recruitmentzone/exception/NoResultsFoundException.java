package za.co.recruitmentzone.exception;

public class NoResultsFoundException extends RuntimeException {

    public NoResultsFoundException(String message) {
        super(message);
    }

    public NoResultsFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoResultsFoundException(Throwable cause) {
        super(cause);
    }
}
