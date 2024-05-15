package za.co.recruitmentzone.client.exception;

import za.co.recruitmentzone.exception.RzoneException;

public class ContactNotFoundException extends RzoneException {

    public ContactNotFoundException(String message) {
        super(message);
    }

    public ContactNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
