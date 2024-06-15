package za.co.recruitmentzone.application.exception;


public class ApplicationsNotFoundException extends ApplicationsException {

    public ApplicationsNotFoundException(String failureMessage) {
        super(failureMessage);
    }
}
