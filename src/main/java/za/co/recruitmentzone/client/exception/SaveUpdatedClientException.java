package za.co.recruitmentzone.client.exception;

public class SaveUpdatedClientException extends RuntimeException {

    public SaveUpdatedClientException(String message) {
        super(message);
    }

    public SaveUpdatedClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public SaveUpdatedClientException(Throwable cause) {
        super(cause);
    }
}
