package za.co.recruitmentzone.candidate.exception;

public class SaveCandidateException extends RuntimeException {

    public SaveCandidateException(String message) {
        super(message);
    }

    public SaveCandidateException(String message, Throwable cause) {
        super(message, cause);
    }

    public SaveCandidateException(Throwable cause) {
        super(cause);
    }
}
