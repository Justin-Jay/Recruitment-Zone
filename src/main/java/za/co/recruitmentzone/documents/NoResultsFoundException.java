package za.co.recruitmentzone.documents;

import za.co.recruitmentzone.exception.RzoneException;

public class NoResultsFoundException extends RzoneException {
    public NoResultsFoundException(String message) {
        super(message);
    }

    public NoResultsFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
