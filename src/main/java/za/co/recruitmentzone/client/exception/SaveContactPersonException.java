package za.co.recruitmentzone.client.exception;

public class SaveContactPersonException extends RuntimeException {

    public SaveContactPersonException(String message) {
        super(message);
    }

    public SaveContactPersonException(String message, Throwable cause) {
        super(message, cause);
    }

    public SaveContactPersonException(Throwable cause) {
        super(cause);
    }
}
