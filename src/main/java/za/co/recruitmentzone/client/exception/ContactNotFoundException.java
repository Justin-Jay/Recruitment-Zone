package za.co.recruitmentzone.client.exception;

import za.co.recruitmentzone.exception.RzoneException;

public class ContactNotFoundException extends RzoneException {

    public ContactNotFoundException(String failureReason) {
        super(failureReason);
    }

    public ContactNotFoundException(String failureReason, Throwable cause) {
        super(failureReason, cause);
    }
}
