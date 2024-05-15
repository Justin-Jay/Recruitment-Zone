package za.co.recruitmentzone.employee.exception;

import za.co.recruitmentzone.exception.RzoneException;

public class TokenTimeOutException extends RzoneException {

    public TokenTimeOutException(String message) {
        super(message);
    }

    public TokenTimeOutException(String message, Throwable cause) {
        super(message, cause);
    }
}
