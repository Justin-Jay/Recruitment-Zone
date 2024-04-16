package za.co.recruitmentzone.client.exception;

public class FileContentException extends RuntimeException {

    public FileContentException(String message) {
        super(message);
    }

    public FileContentException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileContentException(Throwable cause) {
        super(cause);
    }
}
