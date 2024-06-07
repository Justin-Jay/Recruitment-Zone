package za.co.recruitmentzone.exception;

public class RzoneException extends RuntimeException {

    public RzoneException(String failureReason) {
        super(failureReason);
    }

    public RzoneException(String failureReason, Throwable cause) {
        super(failureReason, cause);
    }
}
