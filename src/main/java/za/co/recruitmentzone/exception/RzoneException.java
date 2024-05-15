package za.co.recruitmentzone.exception;

public class RzoneException extends RuntimeException {

    public RzoneException(String message) {
        super(message);
    }

    public RzoneException(String message, Throwable cause) {
        super(message, cause);
    }
}
