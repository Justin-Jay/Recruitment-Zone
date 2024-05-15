package za.co.recruitmentzone.employee.exception;

import za.co.recruitmentzone.exception.RzoneException;

public class CreateEmployeeException extends RzoneException {

    public CreateEmployeeException(String message) {
        super(message);
    }
    public CreateEmployeeException(String message, Throwable cause) {
        super(message, cause);
    }

}
