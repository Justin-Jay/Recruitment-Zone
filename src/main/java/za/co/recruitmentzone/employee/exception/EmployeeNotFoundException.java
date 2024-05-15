package za.co.recruitmentzone.employee.exception;

import za.co.recruitmentzone.exception.RzoneException;

public class EmployeeNotFoundException extends RzoneException {

    public EmployeeNotFoundException(String message) {
        super(message);
    }

    public EmployeeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
