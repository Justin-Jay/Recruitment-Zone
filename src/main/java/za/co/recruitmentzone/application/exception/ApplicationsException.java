package za.co.recruitmentzone.application.exception;

import za.co.recruitmentzone.exception.RzoneException;

public class ApplicationsException extends Exception {

    String failureMessage;

    public ApplicationsException(String failureMessage) {
        this.failureMessage = failureMessage;
    }
    public String getFailureMessage() {
        return failureMessage;
    }
}
