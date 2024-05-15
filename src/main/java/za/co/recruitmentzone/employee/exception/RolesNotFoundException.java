package za.co.recruitmentzone.employee.exception;

import za.co.recruitmentzone.exception.RzoneException;

public class RolesNotFoundException extends RzoneException {
    public RolesNotFoundException(String message) {
        super(message);
    }

    public RolesNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
