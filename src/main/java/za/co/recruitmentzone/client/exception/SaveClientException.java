package za.co.recruitmentzone.client.exception;

public class SaveClientException extends RuntimeException {

    public SaveClientException(String message) {
        super(message);
    }

    public SaveClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public SaveClientException(Throwable cause) {
        super(cause);
    }
}
