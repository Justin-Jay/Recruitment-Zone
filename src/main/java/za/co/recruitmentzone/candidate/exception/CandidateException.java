package za.co.recruitmentzone.candidate.exception;

import za.co.recruitmentzone.exception.RzoneException;

public class CandidateException extends RzoneException {

    public CandidateException(String message) {
        super(message);
    }

    public CandidateException(String failureReason, Throwable cause) {
        super(failureReason, cause);
    }
}
