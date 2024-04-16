package za.co.recruitmentzone.exception;

public class DocumentLocationError extends RuntimeException {

    public DocumentLocationError(String message) {
        super(message);
    }

    public DocumentLocationError(String message, Throwable cause) {
        super(message, cause);
    }

    public DocumentLocationError(Throwable cause) {
        super(cause);
    }
}
