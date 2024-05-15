package za.co.recruitmentzone.exception;

public class NoResultsFoundException extends RzoneException {
    public NoResultsFoundException(String message) {
        super(message);
    }

    public NoResultsFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
