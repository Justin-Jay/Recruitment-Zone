package za.co.recruitmentzone.client.exception;

import za.co.recruitmentzone.exception.RzoneException;

public class SaveContactPersonException extends RzoneException {

    public SaveContactPersonException(String message) {
        super(message);
    }

    public SaveContactPersonException(String message, Throwable cause) {
        super(message, cause);
    }
}
