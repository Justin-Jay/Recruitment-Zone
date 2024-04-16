package za.co.recruitmentzone.application.exception;

public class ApplicationsNotFoundException extends RuntimeException {

    public ApplicationsNotFoundException(String message) {
        super(message);
    }

    public ApplicationsNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationsNotFoundException(Throwable cause) {
        super(cause);
    }
}
