package za.co.recruitmentzone.candidate.exception;

public class CandidateException extends RuntimeException {

    public CandidateException(String message) {
        super(message);
    }

    public CandidateException(String message, Throwable cause) {
        super(message, cause);
    }

    public CandidateException(Throwable cause) {
        super(cause);
    }
}
