package za.co.recruitmentzone.employee.exception;

import za.co.recruitmentzone.exception.RzoneException;

public class EmployeeNotSavedException extends RzoneException {
    public EmployeeNotSavedException(String message) {
        super(message);
    }

    public EmployeeNotSavedException(String message, Throwable cause) {
        super(message, cause);
    }


}
