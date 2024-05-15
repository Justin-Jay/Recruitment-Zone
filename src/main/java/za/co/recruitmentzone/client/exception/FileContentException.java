package za.co.recruitmentzone.client.exception;

import za.co.recruitmentzone.exception.RzoneException;

public class FileContentException extends RzoneException {

    public FileContentException(String message) {
        super(message);
    }

    public FileContentException(String message, Throwable cause) {
        super(message, cause);
    }

}
