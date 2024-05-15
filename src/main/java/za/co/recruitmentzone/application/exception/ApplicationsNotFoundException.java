package za.co.recruitmentzone.application.exception;

import za.co.recruitmentzone.exception.RzoneException;

public class ApplicationsNotFoundException extends RzoneException {

    public ApplicationsNotFoundException(String message) {
        super(message);
    }
}
