package za.co.recruitmentzone.client.exception;

import za.co.recruitmentzone.exception.RzoneException;

public class AddContactPersonException extends RzoneException {

    public AddContactPersonException(String message) {
        super(message);
    }

    public AddContactPersonException(String message, Throwable cause) {
        super(message, cause);
    }

}
