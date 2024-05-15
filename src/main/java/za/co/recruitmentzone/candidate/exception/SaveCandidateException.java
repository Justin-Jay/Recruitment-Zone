package za.co.recruitmentzone.candidate.exception;

import za.co.recruitmentzone.exception.RzoneException;

public class SaveCandidateException extends RzoneException {

    public SaveCandidateException(String message) {
        super(message);
    }

    public SaveCandidateException(String message, Throwable cause) {
        super(message, cause);
    }


}
