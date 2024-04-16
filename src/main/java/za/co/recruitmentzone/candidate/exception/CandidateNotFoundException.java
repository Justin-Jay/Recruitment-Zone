package za.co.recruitmentzone.candidate.exception;

public class CandidateNotFoundException extends RuntimeException {
    public CandidateNotFoundException(String message) {
        super(message);
    }

    public CandidateNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CandidateNotFoundException(Throwable cause) {
        super(cause);
    }
}
