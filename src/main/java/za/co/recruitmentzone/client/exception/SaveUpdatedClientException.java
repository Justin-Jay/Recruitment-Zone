package za.co.recruitmentzone.client.exception;

import za.co.recruitmentzone.exception.RzoneException;

public class SaveUpdatedClientException extends RzoneException {

    public SaveUpdatedClientException(String message) {
        super(message);
    }

    public SaveUpdatedClientException(String message, Throwable cause) {
        super(message, cause);
    }

}
