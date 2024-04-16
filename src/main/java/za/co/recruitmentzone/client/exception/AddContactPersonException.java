package za.co.recruitmentzone.client.exception;

public class AddContactPersonException extends RuntimeException {

    public AddContactPersonException(String message) {
        super(message);
    }

    public AddContactPersonException(String message, Throwable cause) {
        super(message, cause);
    }

    public AddContactPersonException(Throwable cause) {
        super(cause);
    }
}
