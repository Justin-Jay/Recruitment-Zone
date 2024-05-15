package za.co.recruitmentzone.client.exception;

import za.co.recruitmentzone.exception.RzoneException;

public class SaveClientException extends RzoneException {

    public SaveClientException(String message) {
        super(message);
    }

    public SaveClientException(String message, Throwable cause) {
        super(message, cause);
    }

}
