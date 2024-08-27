package za.co.recruitmentzone.candidate.exception;

import za.co.recruitmentzone.exception.RzoneException;

public class CandidateNotFoundException extends RzoneException {
    public CandidateNotFoundException(String message) {
        super(message);
    }

    public CandidateNotFoundException(String failureReason, Throwable cause) {
        super(failureReason, cause);
    }
}
